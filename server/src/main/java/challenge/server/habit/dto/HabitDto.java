package challenge.server.habit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HabitDto {

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        // 응답 DTO
        private Long habitId;
        private String title;
        private String body;
        private Boolean isBooked;
    //  private String photoUrl; 추후 Photo 테이블 결정 후 추가 예정
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class ResponseDetail {
        // 상세 응답 DTO
        private Long habitId;
        private Long userId;
        private String title;
        private String subTitle;
        private String category;
        private String body;
        private String authType;
        private String authStartTime;
        private String authEndTime;
        private Float score;
        private Boolean isBooked;
    }

    // TODO 습관 통계 DTO
    // TODO 습관 후기 DTO
    // TODO 습관 인증 DTO

}
