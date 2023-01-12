package challenge.server.user.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.helper.event.UserRegistrationApplicationEvent;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapper;
import challenge.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    //    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public User createUser(User user) {
        //log.info("-------- createUser 중복 회원 검사 --------");
        //System.out.println(user.getEmail());
        verifyExistUser(user.getEmail()); // todo User 엔티티 작성

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

    public void verifyExistUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXISTS);
        }

    }

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

    public User findUser(Long userId) {
        return findVerifiedUser(userId);
    }

    @Transactional(readOnly = true)
    public User findVerifiedUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User findUser = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }
}
