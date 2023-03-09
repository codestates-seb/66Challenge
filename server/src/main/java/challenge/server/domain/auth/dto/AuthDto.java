package challenge.server.domain.auth.dto;

import challenge.server.domain.auth.entity.Auth;
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
//    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long authId;
        private Long habitId;
        private Long authorUserId;
        private String authorUsername;
        private String body;
        private String createdAt;
        private String authImageUrl;

        @Builder
        public Response(Auth auth) {
            this.authId = auth.getAuthId();
            this.habitId = auth.getChallenge().getHabit().getHabitId();
            this.authorUserId = auth.getChallenge().getUser().getUserId();
            this.authorUsername = auth.getChallenge().getUser().getUsername();
            this.body = auth.getBody();
            this.createdAt = auth.getCreatedAt().toString();
            this.authImageUrl = auth.getAuthImageUrl();
        }
    }
}