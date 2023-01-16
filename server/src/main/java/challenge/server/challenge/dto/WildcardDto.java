package challenge.server.challenge.dto;

import lombok.*;

public class WildcardDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long wildcardId;
        private String createdAt;
    }
}
