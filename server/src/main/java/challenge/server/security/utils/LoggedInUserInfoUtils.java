package challenge.server.security.utils;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.user.entity.User;
import challenge.server.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class LoggedInUserInfoUtils {
    private UserRepository userRepository;

    // 레퍼런스 응용 + 내가 테스트해서 pre-project 때 작성한 코드
    public User extractUser() {
        String username; // 여기서 username = 로그인 요청 시 전달받는 LoginDto 상 username = 회원의 이메일(o) 회원 정보/엔티티 상 회원의 닉네임(x)

        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("로그인한 회원의 이메일 = " + username); // todo 확인용 출력
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.AUTHENTICATION_NOT_FOUND);
        }

        User findUser = userRepository.findByEmail(username).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }

    /*
    public Optional<User> extractOptionalUser() {
        try {

        } catch (Exception e) {

        }
    }
     */

    // pre-project 레퍼런스
    public Long extractUserId() {
        Map<String, Object> claims;

        try {
            claims = (Map) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.AUTHENTICATION_NOT_FOUND);
        }

        System.out.println("로그인한 회원의 회원 식별자/번호 = " + ((Number) claims.get("userId")).longValue()); // todo 확인용 출력
        return ((Number) claims.get("userId")).longValue();
    }

    // pre-project 레퍼런스
    public User extractUserDraft() {
        Long userId = extractUserId();
        System.out.println("로그인한 회원의 회원 식별자/번호 = " + userId); // todo 확인용 출력
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return user;
    }
}

