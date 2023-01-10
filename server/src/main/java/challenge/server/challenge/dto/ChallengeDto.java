package challenge.server.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ChallengeDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        private String habitTitle;
        private String challenger;
        private String status;
        private int usedWildcard;
        private String review;
        private int score;
        private List<Long> authIds;
    }
}
