package challenge.server.user.dto;

import challenge.server.validator.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        // 유효성 검사를 통해 회원 닉네임과 이메일 중복 여부를 확인
        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "닉네임은 공백이 아니어야 합니다")
        private String username;

        // todo 패스워드 유효성 검사 프론트와 합의해서 별도 validation annotation 만들기
        @NotBlank
        @Password
        private String password;
    }

}
