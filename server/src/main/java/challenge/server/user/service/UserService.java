package challenge.server.user.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.helper.event.UserRegistrationApplicationEvent;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.security.utils.LoggedInUserInfoUtils;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapper;
import challenge.server.user.repository.UserRepository;
import challenge.server.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher; // todo 회원 가입 시 이메일 전송 관련
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final LoggedInUserInfoUtils loggedInUserInfoUtils;

    public Boolean verifyExistEmail(String email) {
        Boolean existEmail = false;
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            existEmail = true;
        }

        return existEmail;
    }

    public Boolean verifyExistUsername(String username) {
        Boolean existUsername = false;
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            existUsername = true;
        }

        return existUsername;
    }

    @Transactional
    public User createUser(User user) {
        //log.info("-------- createUser 중복 회원 검사 --------");
        //System.out.println(user.getEmail());
        verifyExistUser(user.getEmail());

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(user.getEmail());
        //log.info("-------- createUser roles --------");
        //System.out.println(roles); // 2023.1.11(수) 3h40 포스트맨 일반 회원 가입 테스트 시 [USER]
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        publisher.publishEvent(new UserRegistrationApplicationEvent(savedUser)); // todo 회원 가입 시 이메일 전송 관련
        return savedUser;
    }

    // todo 회원 정보를 수정하려는 사람이 해당 회원이 맞는지 검증하는 로직이 필요한가? 아니면 요청 받을 때 Access Token을 받으면 그걸로 충분한가?
    @Transactional/*(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)*/
    public User updateUser(User user) {
        User loggedInUser = verifyLoggedInUser(user.getUserId());
        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> loggedInUser.setPassword(passwordEncoder.encode(password)));
        Optional.ofNullable(user.getUsername())
                .ifPresent(username -> loggedInUser.setUsername(username));

        return userRepository.save(loggedInUser);
    }

    public void verifyExistUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }
    }

    public User findUser(Long userId) {
        return findVerifiedUser(userId);
    }

    public User findVerifiedUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User findUser = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }

    public User verifyLoggedInUser(Long userId) {
        User loggedInUser = loggedInUserInfoUtils.extractUser();
        Long loggedInUserId = loggedInUser.getUserId();

        if (loggedInUserId != userId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        return loggedInUser;
    }

}
