package challenge.server.challenge.dto;

import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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
