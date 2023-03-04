package challenge.server.security.filter;

import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.user.dto.LoginDto;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.JsonParser;
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
import java.io.InputStream;
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
    @Override // 로그인 인증을 시도한다.
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

//        InputStream inputStream = request.getInputStream();
//
//        byte[] arr = inputStream.readAllBytes();
//        String str = new String(arr);
//        log.info("str={}",str);
//
//        byte[] arr2 = inputStream.readAllBytes();
//        String str2 = new String(arr2);
//        log.info("str={}",str2);

//        String json = IOUtils.toString(request.getInputStream());
//        LoginDto loginDto = objectMapper.readValue(json, LoginDto.class); // Todo 여전히 여기서 정보를 못 읽긴 하다.. 그런데 login 할 때 제외하고는 여기를 거칠 필요가 없는데, 왜 자꾸 여기로 오는 것일까?

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"), Collections.emptyList());

//        System.out.println("username: " + loginDto.getUsername() + " password: " + loginDto.getPassword());
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override // 인증 성공 시 실행되는 메서드
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {

        User user = (User) authResult.getPrincipal(); // TODO why CustomUserDetails 타입이 아닌지, 이미 여기서 유저 DB 상에 존재하는 유저임을 확인함.
        System.out.println("email = " + user.getEmail() + ", username = " + user.getUsername() + ", password = " + user.getPassword()); // email = user1@naver.com, username = user1@naver.com, password = {bcrypt}$2a$10$EQKLOa1LlHu.jmw.yw3RR.gGKiLwAMcF2reXXu.2fmIk.SVqI6DUy

        String accessToken = delegateAccessToken(user);
        String refreshToken = delegateRefreshToken(user);
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        // 로그인 시 유저상태(quit, banned) 검증 & refreshToken 발급해 저장
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