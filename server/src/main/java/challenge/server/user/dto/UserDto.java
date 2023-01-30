package challenge.server.user.dto;

import challenge.server.user.entity.DaysOfFail;
import challenge.server.user.entity.NumOfAuthByChallenge;
import challenge.server.user.entity.User;
import challenge.server.validator.NotSpace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDto {
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Post {
        /* 클라이언트 단 유효성 검사 정규 표현힉
        const passwordRegExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/; 비밀번호 = 영문자 1개, 숫자 1개, 특수문자 1개 각 반드시 포함해서 8~12글자
        const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*[.][a-zA-Z]{2,3}$/; 이메일 = 이메일 형식
        const userNameRegExp = /[A-Za-z0-9가-힇]{2,20}/; 닉네임 = 영문자 또는 숫자 또는 한글 2~20글자
         */
        // 유효성 검사를 통해 회원 닉네임과 이메일 중복 여부를 확인
        @Email
        @NotBlank(message = "이메일은 공백이 아니어야 하며, 유효한 이메일 주소 형식이어야 합니다.")
        private String email;

        @Pattern(regexp = "[A-Za-z0-9가-힇]{2,8}",
                message = "닉네임은 공백이 아니어야 하며, 영문자/숫자/한글 2~8글자로 이루어집니다.")
        private String username;

        //@Password
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,12}$",
                message = "비밀번호는 공백이 아니어야 하며, 영문자 1개, 숫자 1개, 특수문자 1개를 각각 반드시 포함한 8~12글자로 이루어집니다.")
        private String password;

        private String age;
        private String gender;
    }

    @Getter
    @Setter // UserController
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Patch {
        private Long userId;

        @NotSpace
        private String profileImageUrl;

        @NotSpace
        @Pattern(regexp = "[A-Za-z0-9가-힇]{2,20}",
                message = "닉네임은 공백이 아니어야 하며, 영문자/숫자/한글 2~20글자로 이루어집니다.")
        private String username;

        @NotSpace
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,12}$",
                message = "비밀번호는 공백이 아니어야 하며, 영문자 1개, 숫자 1개, 특수문자 1개를 각각 반드시 포함한 8~12글자로 이루어집니다.")
        //@Password
        private String password;
        private String age;
        private String gender;
        // 대소문자 구분 없이 active, quit, banned 3가지 중 1개 값만 가능한 조건 구현?
//        private String userStatus;
    }

    @Getter
    @Setter // UserController
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class CheckPassword {
        @NotSpace
        private String password;

        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class BanResponse {
        private Long userId;

        private User.Status status;
    }


    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class SimpleResponse {
        private Long userId;
        private String email;
        private String username;
        private String profileImageUrl;
        private String age;
        private String gender;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class PatchResponse {
        private Long userId;

        private String username;

        private String profileImageUrl;
        private String age; // Java에서 숫자로 변환
        private String gender; // female, male

//        private String password;
//        private String userStatus;
    }

    /*
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class DetailResponse {
        private Long userId;

        private String email;

        private String username;

        private String profileImageUrl;

        private int biggestNumOfChallengeHabitDays; // 회원이 진행 중인 challenge 중 가장 높은 진행일을 선택하여 'n일차' 출력

        private List<ChallengeResponse> activeChallenges; // 회원이 참여중 + 참여 완료한 습관 목록을 서브타이틀(및 진행일수)로 표시 -> 이 정보를 담기 위한 별도의 객체가 필요한지?

        private List<CategoryResponse> activeCategories; // 진행 중인 습관들의 카테고리 정보 -> 진행 중인 습관의 분석 데이터를 선택하기 위한 카테고리 선택자
        // 홍보 문구(오늘의 인용구 포함)
    }
     */

    @Getter
    @NoArgsConstructor
    @Setter
    public static class UserDetailsDb {
        private Long userId;
        private String email;
        private String username;
        private String profileImageUrl;
        //        private LocalDateTime earliestCreatedAt;
        private int biggestProgressDays;
        private List<UserDto.ChallengeDetailsDb> activeChallenges;
        private Set<CategoryDb> activeCategories;

        // 통계 데이터 응답
//        private UserDto.StatisticsResponse statisticsResponse;
        List<NumOfAuthByChallenge> numOfAuthByChallengeList;

        // 습관 참여 후 평균 며칠 후 포기하는지 = 챌린지 생성일(Challenge createdAt)로부터 챌린지 실패로 변환된 날(Challenge lastModifiedAt) 평균 값
        List<DaysOfFail> daysOfFailList;
        int averageDaysOfFail;

        // 많이 참여한 습관의 카테고리 = 내가 주로 어떤 카테고리의 습관에 참여하는지
//        @JsonIgnore
        @JsonProperty
        List<UserDto.CategoriesResponse> favoriteCategories;
//        @QueryProjection
//        public UserDetailsDb(Long userId, String email, String username, LocalDateTime earliestCreatedAt) {
//            this.userId = userId;
//            this.email = email;
//            this.username = username;
//            this.earliestCreatedAt = earliestCreatedAt;
//        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChallengeDetailsDb {
        private Long challengeId;
        //        private LocalDateTime createdAt;
        private int progressDays;
        private Long habitId;
        private String subTitle;
//        private Long categoryId;
//        private String type;

//        @QueryProjection
//        public ChallengeDetailsDb(Long challengeId, LocalDateTime createdAt, Long habitId, String subTitle, Long categoryId, String type) {
//            this.challengeId = challengeId;
//            this.createdAt = createdAt;
//            this.habitId = habitId;
//            this.subTitle = subTitle;
//            this.categoryId = categoryId;
//            this.type = type;
//        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryDb {
        private Long categoryId;
        private String type;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class ChallengeResponse {
        private Long challengeId;
        private String habitSubTitle;
        private int authDays;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class CategoryResponse {
        private Long categoryId;
        private String type;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class HabitResponse {
        private Long habitId;
        private String title;
        private String subTitle;
        private String body;
        //        private Long categoryId;
        private Boolean isBooked;
        //        private String hostUsername; // 현재 화면 정의서 상 습관 목록 및 상세 조회 페이지에 host 정보 없는데, 확인해보기
        private String thumbImgUrl; // 사진 저장 방식 결정 필요
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class SuccessHabitCertificate {
        private Long challengeId;
        private String username;
        private String title;
        private LocalDateTime createdAt;
        private LocalDateTime completedAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Redis {
        private Long userId;
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class TokenRequest {
        @NotBlank(message = "accessToken을 입력해 주세요.")
        String accessToken;

        @NotBlank(message = "refreshToken을 입력해 주세요.")
        String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class LogoutRequest {
        @NotBlank(message = "잘못된 요청입니다.")
        String accessToken;

        @NotBlank(message = "잘못된 요청입니다.")
        String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatisticsResponse {
        // 내가 현재까지 올린 인증 글의 갯수 -> 습관별? 전체?
        List<NumOfAuthByChallenge> numOfAuthByChallengeList;

        // 습관 참여 후 평균 며칠 후 포기하는지 = 챌린지 생성일(Challenge createdAt)로부터 챌린지 실패로 변환된 날(Challenge lastModifiedAt) 평균 값
        List<DaysOfFail> daysOfFailList;
        int averageDaysOfFail;

        // 많이 참여한 습관의 카테고리 = 내가 주로 어떤 카테고리의 습관에 참여하는지
        List<UserDto.CategoriesResponse> favoriteCategories;

        // 아침/저녁형인지 = 챌린지별 인증 시간 기반으로 표시

        // 내 또래 사람들이 주로 참여하는 습관의 카테고리
    }

    public static class CategoriesResponse {
        @JsonProperty
        Long count;

        @JsonProperty
        Long categoryId;
    }
}