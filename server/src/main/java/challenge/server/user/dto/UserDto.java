package challenge.server.user.dto;

import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.entity.Habit;
import challenge.server.user.entity.User;
import challenge.server.validator.NotSpace;
import challenge.server.validator.Password;
import challenge.server.category.entity.Category;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

//@ApiModel(value = "회원 관련 DTO")
public class UserDto {
    @ApiModel(value = "회원 가입 요청 시 전달")
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
        @ApiModelProperty(example = "user1@gmail.com", value = "회원의 이메일")
        @Email
        @NotBlank(message = "이메일은 공백이 아니어야 하며, 유효한 이메일 주소 형식이어야 합니다.")
        private String email;

        @ApiModelProperty(example = "유저no1", value = "회원의 닉네임")
        @Pattern(regexp = "[A-Za-z0-9가-힇]{2,20}",
                message = "닉네임은 공백이 아니어야 하며, 영문자/숫자/한글 2~20글자로 이루어집니다.")
        private String username;

        @ApiModelProperty(example = "Iamuser001!", value = "회원의 비밀번호")
        //@Password
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,12}$",
                message = "비밀번호는 공백이 아니어야 하며, 영문자 1개, 숫자 1개, 특수문자 1개를 각각 반드시 포함한 8~12글자로 이루어집니다.")
        private String password;
    }

    @ApiModel(value = "회원 정보 수정 요청 시 전달")
    @Getter
    @Setter // UserController
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Patch {
        @ApiModelProperty(example = "1", value = "회원의 식별자")
        private Long userId;

        @ApiModelProperty(example = "user1번", value = "회원의 닉네임")
        @NotSpace
        @Pattern(regexp = "[A-Za-z0-9가-힇]{2,20}",
                message = "닉네임은 공백이 아니어야 하며, 영문자/숫자/한글 2~20글자로 이루어집니다.")
        private String username;

        @ApiModelProperty(example = "iAmUser01!", value = "회원의 비밀번호")
        @NotSpace
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,12}$",
                message = "비밀번호는 공백이 아니어야 하며, 영문자 1개, 숫자 1개, 특수문자 1개를 각각 반드시 포함한 8~12글자로 이루어집니다.")
        //@Password
        private String password;

        // 대소문자 구분 없이 active, quit, banned 3가지 중 1개 값만 가능한 조건 구현?
//        private String userStatus;
    }

    @ApiModel(value = "비밀번호 확인 요청 시 전달")
    @Getter
    @Setter // UserController
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class CheckPassword {
        @ApiModelProperty(example = "iAmUser01!", value = "회원의 비밀번호")
        @NotSpace
        private String password;

        private Long userId;
    }

    @ApiModel(value = "일정 회수 이상 신고 당한 회원 정지 처리 후 응답")
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class BanResponse {
        @ApiModelProperty(example = "1", value = "회원의 식별자")
        private Long userId;

        @ApiModelProperty(example = "BANNED", value = "회원의 상태(ACTIVE, QUIT, BANNED 중 하나)")
        private User.Status status;
    }


    @ApiModel(value = "간략한 회원 정보 응답 시 전달")
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class SimpleResponse {
        @ApiModelProperty(example = "1", value = "회원의 식별자")
        private Long userId;

        @ApiModelProperty(example = "user1@gmail.com", value = "회원의 이메일")
        private String email;

        @ApiModelProperty(example = "user1번", value = "회원의 닉네임")
        private String username;
    }

    @ApiModel(value = "회원 정보 수정 후 응답 시 전달")
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class PatchResponse {
        @ApiModelProperty(example = "1", value = "회원의 식별자")
        private Long userId;

        @ApiModelProperty(example = "user1번", value = "회원의 닉네임")
        private String username;

//        @ApiModelProperty(example = "iAmUser01!", value = "회원의 비밀번호")
//        private String password;
//        private String userStatus;
    }

    @ApiModel(value = "회원의 마이페이지 조회 응답 시 전달")
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class DetailResponse {
        @ApiModelProperty(example = "1", value = "회원의 식별자")
        private Long userId;

        @ApiModelProperty(example = "user1@gmail.com", value = "회원의 이메일")
        private String email;

        @ApiModelProperty(example = "user1번", value = "회원의 닉네임")
        private String username;

        @ApiModelProperty(example = "47", value = "회원이 진행 중인 습관 중 가장 높은 진행일")
        private int biggestNumOfChallengeHabitDays; // 회원이 진행 중인 challenge 중 가장 높은 진행일을 선택하여 'n일차' 출력

        @ApiModelProperty(example = "[{\"challengeId\": 1, habitSubTitle\": \"미라클모닝\", \"authDays\": 32}, {\"challengeId\": 1, habitSubTitle\": \"미라클모닝\", \"authDays\": 32}, {\"challengeId\": 1, habitSubTitle\": \"미라클모닝\", \"authDays\": 32}]", value = "회원이 참여 중인 & 참여 완료한 습관 목록")
        private List<ChallengeResponse> activeChallenges; // 회원이 참여중 + 참여 완료한 습관 목록을 서브타이틀(및 진행일수)로 표시 -> todo 이 정보를 담기 위한 별도의 객체가 필요한지?

        @ApiModelProperty(example = "[{\"categoryId\": 1, \"type\": \"HEALTH\"}, {\"categoryId\": 1, \"type\": \"HEALTH\"}]", value = "참여 중인 습관들의 카테고리")
        private List<CategoryResponse> activeCategories; // 진행 중인 습관들의 카테고리 정보 -> 진행 중인 습관의 분석 데이터를 선택하기 위한 카테고리 선택자
        // 홍보 문구(오늘의 인용구 포함)
    }

    @Getter
    @NoArgsConstructor
    @Setter
    public static class UserDetailsDb {
        private Long userId;
        private String email;
        private String username;
        //        private LocalDateTime earliestCreatedAt;
        private int biggestProgressDays;
        private List<UserDto.ChallengeDetailsDb> activeChallenges;
        private List<UserDto.CategoryDb> activeCategories;
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
//        private String hostUsername; // todo 현재 화면 정의서 상 습관 목록 및 상세 조회 페이지에 host 정보 없는데, 확인해보기
        // image // todo 사진 저장 방식 결정 필요
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
}