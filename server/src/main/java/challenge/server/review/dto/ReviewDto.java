package challenge.server.review.dto;

import lombok.*;

public class ReviewDto {


    public static class Post {
        private String body;
        private int score;
    }

    public static class Patch {
        private Long reviewId;
        private String body;
        private int score;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private String reviewer;
        private String body;
        private int score;
        private String createdAt;
    }
}
