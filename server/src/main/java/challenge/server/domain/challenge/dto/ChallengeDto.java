package challenge.server.domain.challenge.dto;

import lombok.*;

import java.util.List;

public class ChallengeDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long challengeId;
        private String habitTitle;
        private String habitSubTitle;
        private String challenger;
        private String status;
        private int usedWildcard;
        private List<Long> authIds;
    }
}
