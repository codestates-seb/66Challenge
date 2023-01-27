package challenge.server.security.filter;

import challenge.server.security.dto.LoginDto;
import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.user.dto.LoginDto;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final UserService userService;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // v1
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
//        UserDto.LoginRequest loginDto = objectMapper.readValue(request.getInputStream(), UserDto.LoginRequest.class);

        // 2023.2.1(수) 10h10 v2 = # attemptAuthentication : loginDto.getEmail=null, login.getPassword=null (controller에서는 createdUser.username = 세번째user 찍힘)
//        LoginDto loginDto = LoginDto.builder()
//                .username(request.getParameter("username"))
//                .password(request.getParameter("password"))
//                .build();

        // 10h35 v3 = https://velog.io/@chullll/Spring-Security-JWT-필터-적용-과정
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        LoginDto loginDto = null;

        try {
            loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class); // 여전히 여기서 정보를 못 읽긴 하다.. 그런데 login 할 때 제외하고는 여기를 거칠 필요가 없는데, 왜 자꾸 여기로 오는 것일까?
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("# attemptAuthentication : loginDto.getEmail={}, login.getPassword={}",
                loginDto.getUsername(), loginDto.getPassword());
//        System.out.println(request.getParameter("username") + request.getParameter("password"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        User user = (User) authResult.getPrincipal();
        System.out.println("email = " + user.getEmail() + ", username = " + user.getUsername() + ", password = " + user.getPassword()); // email = user1@naver.com, username = user1@naver.com, password = {bcrypt}$2a$10$EQKLOa1LlHu.jmw.yw3RR.gGKiLwAMcF2reXXu.2fmIk.SVqI6DUy

        String accessToken = delegateAccessToken(user);
        String refreshToken = delegateRefreshToken(user);
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        User findUser = userService.verifyLoginUser(user.getEmail(), user.getPassword(), refreshToken);

        Map<String, Object> body = new HashMap<>();
        body.put("userId", findUser.getUserId()); // 로그인 후 userId를 응답 body에 전달
        body.put("username", findUser.getUsername()); // 2023.2.1(수) 17h 추가

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }

    private String delegateAccessToken(User user) {
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

    private String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration((jwtTokenizer.getRefreshTokenExpirationMinutes()));
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
        return refreshToken;
    }
}
