package challenge.server.user.controller;

import challenge.server.challenge.entity.Challenge;
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

    @ApiOperation(value = "이메일 중복 여부 확인", notes = "true 응답 = 중복되는 이메일 존재 / false 응답 = 중복되는 이메일 없음")
    @GetMapping("/email/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable("email") String email) {
//        return new ResponseEntity<>(userService.verifyExistEmail(email), HttpStatus.OK);

        // API 통신용
//        return new ResponseEntity<>(false, HttpStatus.OK);
        return ResponseEntity.ok(false);
    }

    @ApiOperation(value = "회원 닉네임 중복 여부 확인", notes = "true 응답 = 중복되는 닉네임 존재 / false 응답 = 중복되는 닉네임 없음")
    @GetMapping("/username/{username}/exists")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@PathVariable("username") String username) {
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
    @GetMapping("/habits/{user-id}/category")
    public ResponseEntity getActiveCategories(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        List<UserDto.CategoryResponse> responseDtos = List.of(createCategoryResponseDto(), createCategoryResponseDto(), createCategoryResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "내가 만든 습관 조회")
    @GetMapping("/habits/{user-id}/host")
    public ResponseEntity getHostHabits(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        List<UserDto.HabitResponse> responseDtos = List.of(createHabitResponseDto(), createHabitResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "인증서 발급")
    @GetMapping("/{user_id}/habits/{habit-id}")
    public ResponseEntity getSuccessHabitCertificate(@PathVariable("user-id") @Positive Long user_id,
                                                     @PathVariable("habit-id") @Positive Long habit_id) {
        // API 통신용
        return new ResponseEntity<>(createSucessHabitCertificate(), HttpStatus.OK);
    }

    @ApiOperation(value = "특정 습관 달성 회원 목록 조회(달성 시간 내림차순)")
    @GetMapping("/success")
    public ResponseEntity getSuccessUsersByChallengeCreatedAtDesc(@Positive @RequestParam int page,
                                                                  @Positive @RequestParam int size) {
        // API 통신용
        List<UserDto.SimpleResponse> responseDtos = List.of(createUserSimpleResponseDto(), createUserSimpleResponseDto(), createUserSimpleResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK); // todo
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
                .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut euismod eu nulla sit amet pellentesque. Cras neque augue, laoreet vel blandit volutpat, convallis in velit. Nulla urna arcu, malesuada vel odio tempor, congue elementum est.")
                .categoryId(1L)
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
