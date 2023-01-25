package challenge.server.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginDto {
    private String username;
    private String password;
}
