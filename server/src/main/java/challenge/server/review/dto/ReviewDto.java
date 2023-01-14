package challenge.server.review.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class ReviewDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Post {
        @ApiModelProperty(example = "후기 내용")
        private String body;
        @ApiModelProperty(example = "5", value = "평점")
        private int score;
    }

    @Getter
    @Setter
    public static class Patch {
        @ApiModelProperty(example = "1", value = "후기 식별자")
        private Long reviewId;
        @ApiModelProperty(example = "후기 내용")
        private String body;
        @ApiModelProperty(example = "1",value = "평점")
        private int score;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        @ApiModelProperty(example = "1",value = "후기 식별자")
        private Long reviewId;
        @ApiModelProperty(example = "후기 작성자")
        private String reviewer;
        @ApiModelProperty(example = "후기 내용")
        private String body;
        @ApiModelProperty(example = "5",value = "평점")
        private int score;
        @ApiModelProperty(example = "2023-01-11T00:39:38.155053",value = "후기 작성 일시")
        private String createdAt;
    }
}