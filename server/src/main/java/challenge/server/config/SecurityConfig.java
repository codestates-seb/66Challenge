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
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;

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
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
//                .authenticationEntryPoint(new UserAuthenticationEntryPoint())
                .accessDeniedHandler(new UserAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//                .authorizeHttpRequests(authorize -> authorize
//                        .antMatchers(HttpMethod.POST, "/*/login").permitAll() // 역할 기반 리소스별 접근 권한 부여 필요; , "/", "/**"
//                        .antMatchers(HttpMethod.POST, "/*/users").permitAll()
//                        .antMatchers(HttpMethod.GET, "/*/users/**").permitAll()
//                        .antMatchers(HttpMethod.GET, "/*/users").hasRole("USER")
//                        .antMatchers(HttpMethod.DELETE, "/*/users").hasRole("USER")
//                        .antMatchers(HttpMethod.PATCH, "/*/users").hasRole("USER")
//                        .antMatchers(HttpMethod.GET, "/*/habits").permitAll()
//                        .antMatchers(HttpMethod.GET, "/*/habits/**").permitAll()
//                        .antMatchers(HttpMethod.POST, "/*/habits/**").hasRole("USER")
//                        .antMatchers(HttpMethod.PATCH, "/*/habits/**").hasRole("USER")
//                        .antMatchers(HttpMethod.DELETE, "/*/habits/**").hasRole("USER")
//                        .antMatchers(HttpMethod.GET, "/*/bookmarks/**").hasRole("USER")
//                        .antMatchers(HttpMethod.POST, "/*/bookmarks/**").hasRole("USER")
//                        .antMatchers(HttpMethod.PATCH, "/*/bookmarks/**").hasRole("USER")
//                        .antMatchers(HttpMethod.DELETE, "/*/bookmarks/**").hasRole("USER")
//                        .antMatchers(HttpMethod.GET, "/*/challenges/**").hasRole("USER")
//                        .antMatchers(HttpMethod.POST, "/*/challenges/**").hasRole("USER")
//                        .antMatchers(HttpMethod.PATCH, "/*/challenges/**").hasRole("USER")
//                        .antMatchers(HttpMethod.DELETE, "/*/challenges/**").hasRole("USER")
//                        .antMatchers(HttpMethod.POST, "/*/logout/**").hasRole("USER")
//                        .anyRequest().permitAll()
//                )
//                .authorizeHttpRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .mvcMatchers("/login").permitAll()
//                .mvcMatchers("/logout/**").authenticated()
//                .mvcMatchers("/oauth2/**").permitAll()
//                .mvcMatchers(GET, "/users/usernames/**").permitAll()
//                .mvcMatchers(GET, "/**/users/emails/**").permitAll()
//                .mvcMatchers(GET, "/users/email-verifications/**").permitAll()
//                .mvcMatchers(POST, "/users/**").permitAll()
//                .mvcMatchers(GET, "/users/**").hasRole("USER")
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
//                .and()
//                .authenticationManager(new CustomAuthenticationManager())
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, userService))
                        .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
                        // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                        .userService(customOAuth2UserService));  // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Refresh", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
//            builder.addFilter(new CustomFilter(authenticationManager));

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
//    @Override
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
