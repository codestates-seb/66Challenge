package challenge.server.auth.dto;

import lombok.*;

public class AuthDto {

    @Getter
    @Setter
    public static class Post {
        private String body;
    }

    @Getter
    @Setter
    public static class Patch {
        private String body;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long authId;
        private Long habitId;
        private Long authorUserId;
        private String authorUsername;
        private String body;
        private String createdAt;
        private String authImageUrl;
    }
}