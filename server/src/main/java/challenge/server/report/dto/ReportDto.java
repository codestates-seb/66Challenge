package challenge.server.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportDto {
    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long reportId;
        private String postType;
        private Long postId;
        private String reporter;
        private String type;
    }
}