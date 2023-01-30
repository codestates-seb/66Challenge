package challenge.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailVerificationDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class VerifyEmail {
        @Email
        @NotBlank(message = "이메일은 공백이 아니어야 하며, 유효한 이메일 주소 형식이어야 합니다.")
        private String email;

    }
}
