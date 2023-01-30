package challenge.server.security.service;

import challenge.server.security.filter.JwtAuthenticationFilter;
import challenge.server.user.dto.UserDto;
import challenge.server.user.mapper.UserMapperImpl;
import challenge.server.user.repository.UserRepository;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityService {
    UserService userService;
    PasswordEncoder passwordEncoder;
    JwtAuthenticationFilter jwtAuthenticationFilter;
    UserMapperImpl userMapper;
    UserRepository userRepository;

    /*
    @Transactional
    public SecurityDto.ResponseToken loginUser(User user) {
        System.out.println("sercurityService에서 " + user.getEmail()); // sercurityService에서 greenkey20@naver.
        User findUser = userRepository.findByEmail(user.getEmail()) // 2023.1.27(금) 19h40 null pointer exception 발생
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        // quit이나 banned 상태인 회원은 로그인 불가능 = active 상태인 회원만 로그인 가능
        if (!findUser.getStatus().equals(ACTIVE)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 비밀번호가 맞아야만 로그인 가능
        if (!passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_USER);
        }

        // 로그인 성공 시 인증 정보를 기반으로 JWT 토큰 생성/발급 + refreshToken은 db에 저장
        String accessToken = jwtAuthenticationFilter.delegateAccessToken(findUser);
        String refreshToken = jwtAuthenticationFilter.delegateRefreshToken(findUser);
        // 추후 Redis 도입 시
//        UserDto.Redis redisUser = userMapper.userToRedisUser(findUser);
//        saveRefreshToken(refreshToken, redisUser);
        findUser.saveRefreshToken(refreshToken); // dirty checking -> save() 안 해도 transaction 내에서 영속성 컨텍스트에 차이점 있으면 transaction 단위 끝날 때 commit

        return SecurityDto.ResponseToken.builder()
                .userId(findUser.getUserId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
     */

    private void saveRefreshToken(String refreshToken, UserDto.Redis redisUser) {
    }


}
