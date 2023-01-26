package challenge.server.habit.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

public class HabitDto {
    @Getter
    @Setter
    public static class Post {
        @NotNull
        private Long hostUserId;
        private String title;
        private String subTitle;
        @NotNull
        private String category;
        private String body;
        private String authType;
        private String authStartTime;
        private String authEndTime;
    }

    @Getter
    @Setter
    public static class Patch {
        private String title;
        private String subTitle;
        @NotNull
        private String category;
        private String body;
        @NotNull
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
        private double score;
        private Boolean isBooked;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private String hostUsername;
        private String subTitle;
        private String authType;
        private String authStartTime;
        private String authEndTime;
        private String challengeStatus;
        private Integer challengers;
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
}
