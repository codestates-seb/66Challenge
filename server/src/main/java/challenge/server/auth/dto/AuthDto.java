package challenge.server.auth.dto;

import lombok.*;

public class AuthDto {

    public static class Post {
        private String body;
    }

    public static class Patch {
        private Long authId;
        private String body;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long authId;
        private String body;
        private String createdAt;
    }
}
