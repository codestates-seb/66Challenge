package challenge.server.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class ReportDto {
    @ApiModel(value = "신고 등록 요청 시 전달")
    @Getter
    @Setter
    public static class Post {
        @ApiModelProperty(example = "Review")
        private String postType;
        @ApiModelProperty(example = "1", value = "게시물 식별자")
        private Long postId;
        @ApiModelProperty(example = "1", value = "유저(신고자) 식별자")
        private Long reporter;
        @ApiModelProperty(example = "ABUSE")
        private String reportType;
    }

    @ApiModel(value = "신고 조회 요청 시 전달")
    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Response {
        @ApiModelProperty(example = "1", value = "신고 식별자")
        private Long reportId;
        @ApiModelProperty(example = "Review")
        private String postType;
        @ApiModelProperty(example = "1", value = "게시물 식별자")
        private Long postId;
        @ApiModelProperty(example = "1", value = "유저(신고자) 식별자")
        private Long reporter;
        @ApiModelProperty(example = "ABUSE")
        private String reportType;
    }
}