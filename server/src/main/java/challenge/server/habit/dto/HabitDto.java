package challenge.server.habit.dto;

import challenge.server.habit.entity.AgeRatio;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.entity.SexRatio;
import challenge.server.habit.entity.StatusRatio;
import lombok.*;

import javax.validation.constraints.NotNull;

import static challenge.server.challenge.entity.Challenge.Status.*;
import static java.lang.Math.round;

public class HabitDto {
    @Getter
    @Setter
    public static class Post {
        @NotNull
        private Long hostUserId;
        private String title;
        private String subTitle;
        private String category;
        private String body;
        private String bodyHTML;
        private String authType;
        private String authStartTime;
        private String authEndTime;
    }

    @Getter
    @Setter
    public static class Patch {
        private String title;
        private String subTitle;
        private String category;
        private String body;
        private String bodyHTML;
        private String authType;
        private String authStartTime;
        private String authEndTime;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Overview {
        private Long habitId;
        private Long hostUserId;
        private String title;
        private String body;
        private String thumbImgUrl;
        private Double score;
        private Boolean isBooked;
        private String challengeStatus;
        private Integer day;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private String hostUsername;
        private String hostUserImgUrl;
        private String subTitle;
        private Long categoryId;
        private String category;
        private String bodyHTML;
        private String authType;
        private String authStartTime;
        private String authEndTime;
        private String challengeStatus;
        private Integer challengers;
        private Integer allChallengers;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        private String succImgUrl;
        private String failImgUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDetail {
        Overview overview;
        Detail detail;
        Image image;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseStatistics {
        AgeRatio ageRatio;
        SexRatio sexRatio;
        StatusRatio statusRatio;
    }

}