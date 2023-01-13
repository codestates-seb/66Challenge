package challenge.server.habit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class HabitDto {
    // TODO 이미지 파일 관리

    @Getter
    @Setter
    public static class Post {
        @ApiModelProperty(example = "1", value = "유저(습관 작성자) 식별자")
        private Long userId;
        @ApiModelProperty(example = "매일매일 일기 쓰기")
        private String title;
        @ApiModelProperty(example = "매일일기")
        private String subTitle;
        @ApiModelProperty(example = "자기계발")
        private String category;
        @ApiModelProperty(example = "매일매일 일기를 작성해서 훌륭한 어른이 됩시다.")
        private String body;
        @ApiModelProperty(example = "카메라")
        private String authType;
        @ApiModelProperty(example = "00:00")
        private String authStartTime;
        @ApiModelProperty(example = "24:00")
        private String authEndTime;
    }

    @Getter
    @Setter
    public static class Patch {
        @ApiModelProperty(example = "1",value = "습관 식별자")
        private Long habitId;
        @ApiModelProperty(example = "매일매일 일기 쓰기")
        private String title;
        @ApiModelProperty(example = "매일일기")
        private String subTitle;
        @ApiModelProperty(example = "자기계발")
        private String category;
        @ApiModelProperty(example = "매일매일 일기를 작성해서 훌륭한 어른이 됩시다.")
        private String body;
        @ApiModelProperty(example = "카메라")
        private String authType;
        @ApiModelProperty(example = "00:00")
        private String authStartTime;
        @ApiModelProperty(example = "24:00")
        private String authEndTime;
    }


    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Response {
        // 응답 DTO
        @ApiModelProperty(example = "1",value = "습관 식별자")
        private Long habitId;
        @ApiModelProperty(example = "매일매일 일기 쓰기")
        private String title;
        @ApiModelProperty(example = "매일매일 일기를 작성해서 훌륭한 어른이 됩시다.")
        private String body;
        @ApiModelProperty(example = "true", value = "북마크 여부")
        private Boolean isBooked;
    //  private String photoUrl; 추후 Photo 테이블 결정 후 추가 예정
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class ResponseDetail {
        // 상세 응답 DTO
        @ApiModelProperty(example = "1",value = "습관 식별자")
        private Long habitId;
        @ApiModelProperty(example = "1", value = "유저(습관 작성자) 식별자")
        private Long userId;
        @ApiModelProperty(example = "매일매일 일기 쓰기")
        private String title;
        @ApiModelProperty(example = "매일일기")
        private String subTitle;
        @ApiModelProperty(example = "자기계발")
        private String category;
        @ApiModelProperty(example = "매일매일 일기를 작성해서 훌륭한 어른이 됩시다.")
        private String body;
        @ApiModelProperty(example = "카메라")
        private String authType;
        @ApiModelProperty(example = "00:00")
        private String authStartTime;
        @ApiModelProperty(example = "24:00")
        private String authEndTime;
        @ApiModelProperty(example = "4.7", value = "습관 평점")
        private Float score;
        @ApiModelProperty(example = "true", value = "북마크 여부")
        private Boolean isBooked;
    }
}