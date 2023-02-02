package challenge.server.security.utils;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.user.entity.User;
import challenge.server.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoggedInUserInfoUtils {
    private final UserRepository userRepository;
    private final JwtTokenizer jwtTokenizer;

    // 레퍼런스 응용 + 내가 테스트해서 pre-project 때 작성한 코드
    public User extractUser() {
        String username; // 여기서 username = 로그인 요청 시 전달받는 LoginDto 상 username = 회원의 이메일(o) 회원 정보/엔티티 상 회원의 닉네임(x)
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        try {
            // v1
//            username = SecurityContextHolder.getContext().getAuthentication().getName();

            // v2
            username = jwtTokenizer.getEmailFromToken(request);
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

