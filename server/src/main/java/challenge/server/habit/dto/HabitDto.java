package challenge.server.habit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class HabitDto {
    @ApiModel(value = "습관 등록 요청 시 전달")
    @Getter
    @Setter
    public static class Post {
        @ApiModelProperty(example = "1", value = "유저(습관 작성자) 식별자")
        @NotNull
        private Long hostUserId;
        @ApiModelProperty(example = "매일매일 일기 쓰기")
        private String title;
        @ApiModelProperty(example = "매일일기")
        private String subTitle;
        @ApiModelProperty(example = "자기계발")
        @NotNull
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

    @ApiModel(value = "습관 수정 요청 시 전달")
    @Getter
    @Setter
    public static class Patch {
        @ApiModelProperty(example = "매일매일 일기 쓰기")
        private String title;
        @ApiModelProperty(example = "매일일기")
        private String subTitle;
        @ApiModelProperty(example = "자기계발")
        @NotNull
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


    @ApiModel(value = "습관 간략 정보 조회 응답 시 전달")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Overview {
        // 응답 DTO
        @ApiModelProperty(example = "1",value = "습관 식별자")
        private Long habitId;
        @ApiModelProperty(example = "매일매일 일기 쓰기")
        private String title;
        @ApiModelProperty(example = "매일매일 일기를 작성해서 훌륭한 어른이 됩시다.")
        private String body;
        @ApiModelProperty(example = "true", value = "북마크 여부")
        private Boolean isBooked;
        private String thumbImgUrl;
        private Float score;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        @ApiModelProperty(example = "1", value = "유저(습관 작성자) 식별자")
        private Long hostUserId;
        @ApiModelProperty(example = "매일일기")
        private String subTitle;
        @ApiModelProperty(example = "카메라")
        private String authType;
        @ApiModelProperty(example = "00:00")
        private String authStartTime;
        @ApiModelProperty(example = "24:00")
        private String authEndTime;
        @ApiModelProperty(example = "CHALLENGE/SUCCESS/FAIL/NONE - 도전중/성공/실패/미도전")
        private String challengeStatus;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        private String succImgUrl;
        private String failImgUrl;
    }

    @ApiModel(value = "습관 상세 정보 조회 응답 시 전달")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDetail {
        Overview overview;
        Detail detail;
        Image image;
    }
}
