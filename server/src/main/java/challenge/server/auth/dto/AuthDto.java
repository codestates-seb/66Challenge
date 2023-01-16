package challenge.server.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class AuthDto {

    @ApiModel(value = "인증글 등록 요청 시 전달")
    @Getter
    @Setter
    public static class Post {
        @ApiModelProperty(example = "인증글 내용")
        private String body;
        // photo 테이블 구현 후 url 추가
    }

    @ApiModel(value = "인증글 수정 요청 시 전달")
    @Getter
    @Setter
    public static class Patch {
        @ApiModelProperty(example = "1", value = "인증글 식별자")
        private Long authId;
        @ApiModelProperty(example = "인증글 내용")
        private String body;
        // photo 테이블 구현 후 url 추가
    }

    @ApiModel(value = "인증글 조회 응답 시 전달")
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
        // photo 테이블 구현 후 url 추가
    }
}
