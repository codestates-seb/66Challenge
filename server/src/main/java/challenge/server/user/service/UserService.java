package challenge.server.user.service;

import challenge.server.helper.event.UserRegistrationApplicationEvent;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.user.entity.User;
import challenge.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher; // todo 회원 가입 시 이메일 전송 관련
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public User createUser(User user) {
//        verifyExistUser(user.getEmail()); // todo User 엔티티 작성

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(user.getEmail());
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
}
