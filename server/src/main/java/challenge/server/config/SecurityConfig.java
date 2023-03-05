package challenge.server.config;

import challenge.server.security.oauth.handler.OAuth2MemberSuccessHandler;
import challenge.server.security.oauth.service.CustomOAuth2UserService;
import challenge.server.security.filter.JwtAuthenticationFilter;
import challenge.server.security.filter.JwtVerificationFilter;
import challenge.server.security.handler.UserAccessDeniedHandler;
import challenge.server.security.handler.UserAuthenticationEntryPoint;
import challenge.server.security.handler.UserAuthenticationFailureHandler;
import challenge.server.security.handler.UserAuthenticationSuccessHandler;
import challenge.server.security.jwt.JwtTokenizer;
import challenge.server.security.utils.CustomAuthorityUtils;
import challenge.server.user.service.LogoutListService;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig { // https 적용
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserService userService;
    private final LogoutListService logoutListService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .formLogin().disable() // 기본으로 제공하는 form 로그인 인증 기능 -> 비활성화
                .httpBasic().disable() // Headers에 id,pw를 담아 인증하는 방식 -> 비활성화
                .headers().frameOptions().sameOrigin()
                .and()
                .exceptionHandling()
                // 인증이 필요하지 않은 페이지에 대해서 @CrossOrigin, 인증이 필요한 페이지에 대해 Security Filter Chain으로 검증.
                .authenticationEntryPoint(new UserAuthenticationEntryPoint()) //Oauth2에서는 인증에서 실패했을때 처리하는 로직
                .accessDeniedHandler(new UserAccessDeniedHandler()) //인가 에러 핸들링
                .and()
                // customFilterConfigurer 내에서 JwtAuthenticationFilter와 VerificationFilter를 추가
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                // v1
//                .authorizeRequests().anyRequest().permitAll() // jwt를 사용하지 않고 있는거네?
//                .and()
                // v2
//                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers("/*/login").permitAll()
//                        .antMatchers("/*/logout/**").authenticated()
//                        .antMatchers("/*/oauth2/**").permitAll()
//                        .antMatchers(GET, "/*/users/usernames/**").permitAll()
//                        .antMatchers(GET, "/*/users/emails/**").permitAll()
//                        .antMatchers(GET, "/*/users/email-verifications/**").permitAll()
//                        .antMatchers(POST, "/*/users/**").permitAll()
//                        .antMatchers(GET, "/*/users/**").hasAnyRole("USER")
//                        .antMatchers(PATCH, "/*/users/**").authenticated()
//                        .antMatchers(DELETE, "/*/users/**").authenticated()
//                        .antMatchers("/*/challenges/**").authenticated()
//                        .antMatchers(POST, "/*/auths/**").authenticated()
//                        .antMatchers(PATCH, "/*/auths/**").authenticated()
//                        .antMatchers(DELETE, "/*/auths/**").authenticated()
//                        .antMatchers(GET, "/*/auths/**").permitAll()
//                        .antMatchers(POST, "/*/reviews/**").authenticated()
//                        .antMatchers(PATCH, "/*/reviews/**").authenticated()
//                        .antMatchers(DELETE, "/*/reviews/**").authenticated()
//                        .antMatchers("/*/reports/**").authenticated()
//                        .antMatchers(GET, "/*/reviews/**").permitAll()
//                        .antMatchers(POST, "/*/habits/**").authenticated()
//                        .antMatchers(PATCH, "/*/habits/**").authenticated()
//                        .antMatchers(DELETE, "/*/habits/**").authenticated()
//                        .antMatchers(GET, "/*/habits/**").permitAll()
//                        .anyRequest().permitAll()
//                )
                //v3
//                .authorizeHttpRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .mvcMatchers("/login").permitAll()
//                .mvcMatchers("/logout/**").authenticated()
//                .mvcMatchers("/oauth2/**").permitAll()
//                .mvcMatchers(GET, "/users/usernames/**").permitAll()
//                .mvcMatchers(GET, "/**/users/emails/**").permitAll()
//                .mvcMatchers(GET, "/users/email-verifications/**").permitAll()
//                .mvcMatchers(POST, "/users/**").permitAll()
//                .mvcMatchers(GET, "/users/**").authenticated()
//                .mvcMatchers(PATCH, "/users/**").authenticated()
//                .mvcMatchers(DELETE, "/users/**").authenticated()
//                .mvcMatchers("/challenges/**").authenticated()
//                .mvcMatchers(POST, "/auths/**").authenticated()
//                .mvcMatchers(PATCH, "/auths/**").authenticated()
//                .mvcMatchers(DELETE, "/auths/**").authenticated()
//                .mvcMatchers(GET, "/auths/**").permitAll()
//                .mvcMatchers(POST, "/reviews/**").authenticated()
//                .mvcMatchers(PATCH, "/reviews/**").authenticated()
//                .mvcMatchers(DELETE, "/reviews/**").authenticated()
//                .mvcMatchers("/reports/**").authenticated()
//                .mvcMatchers(GET, "/reviews/**").permitAll()
//                .mvcMatchers(POST, "/habits/**").authenticated()
//                .mvcMatchers(PATCH, "/habits/**").authenticated()
//                .mvcMatchers(DELETE, "/habits/**").authenticated()
//                .mvcMatchers(GET, "/habits/**").permitAll()
//                .anyRequest().permitAll()
//                .authenticationManager(new CustomAuthenticationManager()) // 필요 없는 듯
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, userService))
                        .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
                        // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                        .userService(customOAuth2UserService));  // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시

        return http.build();
    }

    /**
     * 삭제와 생성을 할수있도록 spring bean 등록해야함.
     * bean설정을 따로 해줘야한다. 안하면 service단에서 빈을 자동생성 못함.
     *
     * CORS 관련 설정
     */
    @Bean // Source 객체
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000","https://66challenge.shop"
                ,"http://66challenge.shop","https://66challenge-server.store","http://66challenge-server.store",
                "chrome-extension://ggnhohnkfcpcanfekomdkjffnfcjnjam"));
        configuration.setExposedHeaders(Arrays.asList("Refresh", "Authorization","Access-Control-Allow-Origin"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, userService);
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, logoutListService);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }

//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    AuthenticationManager ldapAuthenticationManager(
//            BaseLdapPathContextSource contextSource) {
//        LdapBindAuthenticationManagerFactory factory =
//                new LdapBindAuthenticationManagerFactory(contextSource);
//        factory.setUserDnPatterns("uid={0},ou=people");
//        factory.setUserDetailsContextMapper(new PersonContextMapper());
//        return factory.createAuthenticationManager();
//    }
}
