package challenge.server.security.filter;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.security.dto.LoginDto;
import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.user.entity.User;
import challenge.server.user.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static challenge.server.user.entity.User.Status.ACTIVE;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        log.info("# attemptAuthentication : loginDto.getEmail={}, login.getPassword={}",
                loginDto.getUsername(),loginDto.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    @Transactional
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        User user = (User) authResult.getPrincipal();
        System.out.println("email = " + user.getEmail() + ", username = " + user.getUsername());

        User findUser = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        // quit이나 banned 상태인 회원은 로그인 불가능 = active 상태인 회원만 로그인 가능
        if (!findUser.getStatus().equals(ACTIVE)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 비밀번호가 맞아야만 로그인 가능
        if (!passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        String accessToken = delegateAccessToken(user);
        String refreshToken = delegateRefreshToken(user);
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        findUser.saveRefreshToken(refreshToken);

        Map<String, Long> body = new HashMap<>();
        body.put("userId", user.getUserId()); // 로그인 후 userId를 응답 body에 전달

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }

    public String delegateAccessToken(User user) { // todo 원래 private 메서드였는데, 특별한 이유가 있을까?
        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles());

        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
        return accessToken;
    }

    public String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration((jwtTokenizer.getRefreshTokenExpirationMinutes()));
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
        return refreshToken;
    }
}
