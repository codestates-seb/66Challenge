package challenge.server.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
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
        @ApiModelProperty(example = "1", value = "챌린지 식별자")
        private Long challengeId;
        @ApiModelProperty(example = "습관 타이틀")
        private String habitTitle;
        @ApiModelProperty(example = "습관 참여자")
        private String challenger;
        @ApiModelProperty(example = "습관 상태")
        private String status;
        @ApiModelProperty(example = "2",value = "습관 참여자가 사용한 와일드카드 개수")
        private int usedWildcard;
        @ApiModelProperty(example = "습관 후기")
        private String review;
        @ApiModelProperty(example = "5",value = "습관 평점")
        private int score;
        @ApiModelProperty(example = "{1, 2, 3, 4, 5, 6, 7}",value = "인증한 게시물들의 식별자")
        private List<Long> authIds;
    }
}
