package challenge.server.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "로그인 요청 시 전달")
@Getter
public class LoginDto {
    private String username;
    private String password;
}
