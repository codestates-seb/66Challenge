package challenge.server.security.oauth.service;

import challenge.server.security.oauth.dto.OAuth2CustomUser;
import challenge.server.security.oauth.dto.OAuthAttributes;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.security.user.entity.User;
import challenge.server.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);  // OAuth2 정보를 가져옵니다.

        Map<String, Object> originAttributes = oAuth2User.getAttributes();  // OAuth2User의 attribute

        // OAuth2 서비스 id (google, kakao, naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 소셜 정보를 가져옵니다.

        // OAuthAttributes: OAuth2User의 attribute를 서비스 유형에 맞게 담아줄 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);
        User user = saveOrUpdate(attributes);
        String email = user.getEmail();
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(email);

        return new OAuth2CustomUser(registrationId, originAttributes, authorities, email);
//        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoles().get(0))),
//                attributes.getAttributes(), attributes.getNameAttributesKey());
    }

    private User saveOrUpdate(OAuthAttributes authAttributes) {
        log.info("email = {}, nickName = {}, profileImageUrl = {}, gender = {}, angeRange = {}",
                authAttributes.getEmail(), authAttributes.getName(), authAttributes.getProfileImageUrl(),
                authAttributes.getGender(), authAttributes.getAgeRange());
        User user = userRepository.findByEmail(authAttributes.getEmail())
                .map(entity -> entity.update(authAttributes.getName(), authAttributes.getProfileImageUrl()))
                .orElse(authAttributes.toEntity());
        return userRepository.save(user);
    }
}
