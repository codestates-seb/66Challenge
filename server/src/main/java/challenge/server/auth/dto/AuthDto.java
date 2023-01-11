package challenge.server.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class AuthDto {

    @Getter
    @Setter
    public static class Post {
        @ApiModelProperty(example = "인증글 내용")
        private String body;
    }

    @Getter
    @Setter
    public static class Patch {
        @ApiModelProperty(example = "1", value = "인증글 식별자")
        private Long authId;
        @ApiModelProperty(example = "인증글 내용")
        private String body;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        @ApiModelProperty(example = "1", value = "인증글 식별자")
        private Long authId;
        @ApiModelProperty(example = "인증글 내용")
        private String body;
        @ApiModelProperty(example = "인증글 등록일시")
        private String createdAt;
    }
}
