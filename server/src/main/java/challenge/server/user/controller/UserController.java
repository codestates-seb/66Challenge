package challenge.server.user.controller;

import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.controller.HabitController;
import challenge.server.habit.dto.HabitDto;
import challenge.server.habit.entity.Habit;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapper;
import challenge.server.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Api
@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final HabitController habitController;

    @ApiOperation(value = "이메일 중복 여부 확인", notes = "true 응답 = 중복되는 이메일 존재 / false 응답 = 중복되는 이메일 없음")
    @GetMapping("/emails/check")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam @Email String email) {
//        return new ResponseEntity<>(userService.verifyExistEmail(email), HttpStatus.OK);

        // API 통신용
//        return new ResponseEntity<>(false, HttpStatus.OK);
        return ResponseEntity.ok(false);
    }

    @ApiOperation(value = "회원 닉네임 중복 여부 확인", notes = "true 응답 = 중복되는 닉네임 존재 / false 응답 = 중복되는 닉네임 없음")
    @GetMapping("/usernames/check")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@RequestParam @NotBlank String username) {
//        return new ResponseEntity<>(userService.verifyExistUsername(username), HttpStatus.OK);

        // API 통신용
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 가입", notes = "Sign Up 버튼을 클릭할 경우 회원 가입 요청을 보냅니다.")
    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post requestBody) {
        User user = userMapper.userPostDtoToUser(requestBody);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(userMapper.userToUserSimpleResponseDto(createdUser), HttpStatus.CREATED);

        // API 통신용
//        return new ResponseEntity<>(createSimpleResponseDto(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "회원 정보 수정")
    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(@PathVariable("user-id") @Positive Long userId,
                                    @Valid @RequestBody UserDto.Patch requestBody) {
//        User updateUser = userService.updateUser(userId);

        // API 통신용
        return new ResponseEntity<>(createUserPatchResponseDto(), HttpStatus.OK); // todo 회원 정보 수정 후 어떤 화면으로 연결/이동하지?
    }

    // todo 관리자가 처리하거나, 또는 특정 조건이 만족되었을 때에 이벤트 발생시켜 처리
    /*
    @ApiOperation(value = "5회 이상 신고 당한 회원 정지")
    @PatchMapping("/reports/{user-id}")
    public ResponseEntity banUser(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        return new ResponseEntity<>(createUserBanResponseDto(), HttpStatus.OK);
    }
     */

    @ApiOperation(value = "회원 개인 정보 통합 조회(마이페이지)")
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") @Positive Long userId) {
        /*
        User findUser = userService.findUser(userId);
        return new ResponseEntity<>(userMapper.userToUserDetailResponseDto(findUser), HttpStatus.OK);
         */

        // API 통신용
        return new ResponseEntity<>(createUserDetailResponseDto(), HttpStatus.OK);
    }

    @ApiOperation(value = "내가 진행 중인 습관의 카테고리 조회")
    @GetMapping("/{user-id}/habits/categories")
    public ResponseEntity getActiveCategories(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        List<UserDto.CategoryResponse> responseDtos = List.of(createCategoryResponseDto(), createCategoryResponseDto(), createCategoryResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    /* bookmark(습관 찜하기) 등록 = habit controller에 해당 요청 처리하는 핸들러 메서드 postBookmark() 있음
    userId 및 habitId로 요청,
    비로그인 회원은 로그인 페이지로 이동,
    해당 습관 제작자는 해당 버튼이 보이지 않음
     */
    // 회원이 찜한 습관들의 목록 출력
    @ApiOperation(value = "회원이 찜한 습관들의 목록 출력")
    @GetMapping("/{user-id}/bookmarks")
    public ResponseEntity getBookmarks(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        List<HabitDto.Response> responses = List.of(habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @ApiOperation(value = "내가 만든 습관 조회")
    @GetMapping("/{user-id}/habits/hosts")
    public ResponseEntity getHostHabits(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        List<UserDto.HabitResponse> responseDtos = List.of(createHabitResponseDto(), createHabitResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "인증서 발급")
    @GetMapping("/{user-id}/habits/{habit-id}/certificates")
    public ResponseEntity getHabitCertificate(@PathVariable("habit-id") @Positive Long habit_id,
                                              @PathVariable("user-id") @Positive Long user_id) {
        // API 통신용
        return new ResponseEntity<>(createSucessHabitCertificate(), HttpStatus.OK);
    }

    /* 2023.1.13(금) 15h10 habit controller가 처리하는 것이 맞음!
    @ApiOperation(value = "특정 습관 달성 회원 목록 조회(달성 시간 내림차순)")
    @GetMapping("/challenges/success")
    public ResponseEntity getSuccessUsers(@Positive @RequestParam int page,
                                          @Positive @RequestParam int size) {
        // API 통신용
        List<UserDto.SimpleResponse> responseDtos = List.of(createUserSimpleResponseDto(), createUserSimpleResponseDto(), createUserSimpleResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
     */

    @ApiOperation(value = "비밀번호 일치 여부 확인", notes = "true 응답 = 비밀번호 일치 / false = 비밀번호 불일치")
    @GetMapping("/{user-id}/passwords/check")
    public ResponseEntity<Boolean> checkPasswordCorrect(@PathVariable("user-id") @Positive Long userId,
                                                        @Valid @RequestBody UserDto.CheckPassword requestBody) {
        // API 통신용
        return ResponseEntity.ok(true);
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UserDto.SimpleResponse createUserSimpleResponseDto() {
        return UserDto.SimpleResponse.builder()
                .userId(1L)
                .email("user1@gmail.com")
                .username("user1번")
                .build();
    }

    private UserDto.PatchResponse createUserPatchResponseDto() {
        return UserDto.PatchResponse.builder()
                .userId(1L)
                .username("유저no1")
                .password("Abc12&defg")
                .build();
    }

    private UserDto.BanResponse createUserBanResponseDto() {
        return UserDto.BanResponse.builder()
                .userId(1L)
                .status(User.Status.BANNED)
                .build();
    }

    private UserDto.DetailResponse createUserDetailResponseDto() {
        return UserDto.DetailResponse.builder()
                .userId(1L)
                .email("user1@gmail.com")
                .username("user1번")
                .biggestNumOfChallengeHabitDays(51)
                .activeChallenges(List.of(createChallengeResponseDto(), createChallengeResponseDto(), createChallengeResponseDto(), createChallengeResponseDto()))
                .activeCategories(List.of(createCategoryResponseDto()))
                .build();
    }

    private UserDto.ChallengeResponse createChallengeResponseDto() {
        return UserDto.ChallengeResponse.builder()
                .challengeId(1L)
                .habitSubTitle("미라클모닝")
                .authDays(32)
                .build();
    }

    private UserDto.CategoryResponse createCategoryResponseDto() {
        return UserDto.CategoryResponse.builder()
                .categoryId(1L)
                .type("HEALTH")
                .build();
    }

    private UserDto.HabitResponse createHabitResponseDto() {
        return UserDto.HabitResponse.builder()
                .habitId(1L)
                .title("새벽 4시30분 기상 - 미라클 모닝")
                .subTitle("아침루틴")
                .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut euismod eu nulla sit amet pellentesque. Cras neque augue, laoreet vel blandit volutpat, convallis in velit. Nulla urna arcu, malesuada vel odio tempor, congue elementum est.")
//                .categoryId(1L)
//                .hostUsername("유저no1")
                .isBooked(true)
                .build();
    }

    private UserDto.SuccessHabitCertificate createSucessHabitCertificate() {
        return UserDto.SuccessHabitCertificate.builder()
                .challengeId(1L)
                .username("유저no1")
                .title("새벽 4시30분 기상 - 미라클 모닝")
                .createdAt("2022-04-10")
                .completedAt("2022-06-15")
                .build();
    }
}
