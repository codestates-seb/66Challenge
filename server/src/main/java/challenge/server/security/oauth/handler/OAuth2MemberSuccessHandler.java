package challenge.server.security.oauth.handler;

import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.security.oauth.dto.OAuth2CustomUser;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();

        String email = oAuth2User.getEmail(); // OAuth2User로부터 Resource Owner의 이메일 주소를 얻음 객체로부터

        List<String> authorities = authorityUtils.createRoles(email);           // 권한 정보 생성
        authorities.stream().map(authoritie -> authoritie.replaceFirst("a", "")).collect(Collectors.toList());

        redirect(request, response, email, authorities);  // Access Token과 Refresh Token을 Frontend에 전달하기 위해 Redirect
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String email, List<String> authorities) throws IOException {
        log.info("Token 생성 시작");
        String accessToken = delegateAccessToken(email, authorities);  // Access Token 생성
        String refreshToken = delegateRefreshToken(email);     // Refresh Token 생성
        User user = userService.findByEmail(email);
        Long userId = user.getUserId();

        userService.verifyLoginUser(email, refreshToken);

        String uri = createURI(accessToken, refreshToken, userId).toString();   // Access Token과 Refresh Token을 포함한 URL을 생성
        getRedirectStrategy().sendRedirect(request, response, uri);   // sendRedirect() 메서드를 이용해 Frontend 애플리케이션 쪽으로 리다이렉트
    }

    private String delegateAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);

        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken, Long userId) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("user_id", String.valueOf(userId));
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("66challenge.shop")
//                .port(3000)
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}