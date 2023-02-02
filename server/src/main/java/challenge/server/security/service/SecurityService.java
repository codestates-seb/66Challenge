package challenge.server.security.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.security.dto.SecurityDto;
import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.LogoutList;
import challenge.server.user.entity.User;
import challenge.server.user.repository.LogoutListRepository;
import challenge.server.user.repository.UserRepository;
import challenge.server.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static challenge.server.user.entity.User.Status.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SecurityService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final LogoutListRepository logoutListRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    @Transactional
    public SecurityDto.ResponseToken loginUser(User user) {
        System.out.println("sercurityService에서 " + user.getEmail()); // sercurityService에서 greenkey20@naver.com
        User findUser = userService.findByEmail(user.getEmail()); // 2023.1.27(금) 19h40 null pointer exception 발생 -> 2023.2.2(목) 9h15 해결

        // quit이나 banned 상태인 회원은 로그인 불가능 = active 상태인 회원만 로그인 가능
        if (!findUser.getStatus().equals(ACTIVE)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 비밀번호가 맞아야만 로그인 가능
        if (!passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 로그인 성공 시 인증 정보를 기반으로 JWT 토큰 생성/발급 + refreshToken은 db에 저장
        String accessToken = delegateAccessToken(findUser);
        String refreshToken = delegateRefreshToken(findUser);
        // 추후 Redis 도입 시
//        UserDto.Redis redisUser = userMapper.userToRedisUser(findUser);
//        saveRefreshToken(refreshToken, redisUser);
        findUser.saveRefreshToken(refreshToken); // dirty checking -> save() 안 해도 transaction 내에서 영속성 컨텍스트에 차이점 있으면 transaction 단위 끝날 때 commit

        return SecurityDto.ResponseToken.builder()
                .userId(findUser.getUserId())
                .username(findUser.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void reissueToken(UserDto.TokenRequest requestBody, HttpServletResponse response) throws ServletException, IOException {
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = requestBody.getRefreshToken();
        Boolean isRefresh = requestBody.getIsRefresh(); // true(Refresh token도 Access token과 함께/같이 갱신) vs false(Access token만 갱신)

        // refreshToken 검증
        if (!jwtTokenizer.validateToken(refreshToken, base64EncodedSecretKey)) {
            throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_NOT_VALID);
        }

        // accessToken에서 user email 가져옴
//        Authentication authentication = jwtVerificationFilter.getAuthentication(accessToken, base64EncodedSecretKey);
        // 2023.1.30(월) 16h accessToken을 안 받기로 함 -> 로그인 시 users 테이블에 저장해둔 refreshToken으로 회원 검색
        Optional<User> optionalUser = userRepository.findByRefreshToken(refreshToken);
        User findUser = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_NOT_VALID)); // 로그아웃되어 DB/Redis에 refreshToken이 존재하지 않는 경우 포함

        // 새로운 토큰 생성
        reissueAccessToken(findUser, response);

        String newRefreshToken = null;
        if (isRefresh) { // refresh token도 갱신해야 하는 경우
            newRefreshToken = reissueRefreshToken(findUser, response);
        } else {
            newRefreshToken = refreshToken;
        }

        // DB/Redis에 새로운 refreshToken 업데이트
        findUser.setRefreshToken(newRefreshToken);
        userRepository.save(findUser); // dirty checking으로 별도 저장 안 해도 된다?
    }

    @Transactional
    public void logout(UserDto.LogoutRequest requestBody) {
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = requestBody.getAccessToken().substring(7);
        String refreshToken = requestBody.getRefreshToken();

        // accessToken 검증
        if (!jwtTokenizer.validateToken(accessToken, base64EncodedSecretKey)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_TOKEN_NOT_VALID);
        }

        // accessToken에서 user email 가져옴
        Authentication authentication = getAuthentication(accessToken, base64EncodedSecretKey);

        // DB/Redis에서 user email을 기반으로 저장된 refreshToken 값을 가져옴
        User findUser = userService.findByEmail(authentication.getName());
        String refreshTokenInDb = findUser.getRefreshToken();

        // DB/Redis에 refreshToken이 존재하는 경우 삭제
        if (refreshTokenInDb != null) {
            findUser.setRefreshToken(null);
        }

        // 해당 accessToken 유효시간 가지고 와서 blackList로 저장하기
        Long expiration = jwtTokenizer.getExpirationFromToken(accessToken, base64EncodedSecretKey);

        LogoutList logoutList = LogoutList.builder()
                .accessToken(accessToken)
                .expiration(expiration).
                build();

        logoutListRepository.save(logoutList);
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

    private String reissueRefreshToken(User user, HttpServletResponse response) throws ServletException, IOException {
        String refreshToken = delegateRefreshToken(user);

        response.setHeader("Refresh", refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

//        Map<String, Long> body = new HashMap<>();
//        body.put("userId", user.getUserId()); // 로그인 후 userId를 응답 body에 전달

//        new ObjectMapper().writeValue(response.getOutputStream(), body);

        return refreshToken;
    }

    private void reissueAccessToken(User user, HttpServletResponse response) {
        String accessToken = delegateAccessToken(user);
        response.setHeader("Authorization", "Bearer " + accessToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

//        return accessToken;
    }

    // JWT 토큰 복호화해서 토큰에 들어있는 정보 꺼냄
    public Authentication getAuthentication(String accessToken, String base64EncodedSecretKey) {
        // 토큰 복호화
        Jws<Claims> claims = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey);

        if (claims.getBody().getSubject() == null) { // 회원 email
            throw new BusinessLogicException(ExceptionCode.AUTHENTICATION_NOT_FOUND);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.getBody().getSubject().toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new org.springframework.security.core.userdetails.User(claims.getBody().getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
