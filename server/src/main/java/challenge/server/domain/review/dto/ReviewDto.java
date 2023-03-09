package challenge.server.domain.review.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

public class ReviewDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Post {
        @NotNull
        private String body;
        @NotNull
        private int score;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch {
        @NotNull
        private Long reviewId;
        private String body;
        private int score;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long reviewId;
        private Long reviewerUserId;
        private String reviewerUsername;
        private String body;
        private int score;
        private String createdAt;
        private String lastModifiedAt;
    }
}
