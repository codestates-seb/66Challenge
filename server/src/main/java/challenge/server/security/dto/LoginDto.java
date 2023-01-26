package challenge.server.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginDto {
    private String username;
    private String password;
}
