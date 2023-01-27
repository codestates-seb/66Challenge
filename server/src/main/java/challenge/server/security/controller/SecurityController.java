package challenge.server.security.controller;

import challenge.server.security.dto.LoginDto;
import challenge.server.security.dto.SecurityDto;
import challenge.server.security.service.SecurityService;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapperImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
//@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class SecurityController {
    private final UserMapperImpl userMapper;
    private final SecurityService securityService;

    /*
    @PostMapping("/login")
    public void postLogin(@Valid @RequestBody SecurityDto.LoginRequestDto requestBody,
                                               HttpServletResponse response) throws IOException {
        System.out.println("securityController에서 " + requestBody.getUsername()); // greenkey20@naver.com
        User user = userMapper.loginRequestDtoToUser(requestBody);
        SecurityDto.ResponseToken responseToken = securityService.loginUser(user);
        System.out.println("securityController로 넘어온 access token = " + responseToken.getAccessToken());

        response.setHeader("Authorization", "Bearer " + responseToken.getAccessToken());
        response.setHeader("Refresh", responseToken.getRefreshToken());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        Map<String, Long> body = new HashMap<>();
        body.put("userId", user.getUserId()); // 로그인 후 userId를 응답 body에 전달

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
     */
}
