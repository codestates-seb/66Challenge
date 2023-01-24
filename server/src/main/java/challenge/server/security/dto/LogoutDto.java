package challenge.server.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel(value = "로그아웃 요청 시 전달")
@Getter
public class LogoutDto {
    private String accessToken;
}
