package challenge.server.user.controller;

import challenge.server.bookmark.service.BookmarkService;
import challenge.server.file.service.FileUploadService;
import challenge.server.habit.entity.Habit;
import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapperImpl;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapperImpl userMapper;
    private final BookmarkService bookmarkService;
    private final FileUploadService fileUploadService;

    @GetMapping("/emails/check")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam @Email String email) {
        return new ResponseEntity<>(userService.verifyExistEmail(email), HttpStatus.OK);
    }

    @GetMapping("/usernames/check")
    public ResponseEntity<Boolean> checkUsernameDuplicate(@RequestParam @NotBlank String username) {
        return new ResponseEntity<>(userService.verifyExistUsername(username), HttpStatus.OK);
    }

    // 회원 가입 시 이메일 인증 요청
    @PostMapping("/email-verification-requests")
    public ResponseEntity sendEmailVerificationMail(@RequestParam @Email String email) throws MessagingException {
        userService.sendEmailVerificationMail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 가입 시 이메일 인증 처리
    // todo 회원이 자신의 이메일에 온 링크를 클릭하면 이 요청이 될 줄 알았는데, 아닌 것 같음(코드 만료 시간이 지나도 내부 처리 없이 그냥 완료되었다고 텍스트가 뜸) vs Postman에서 요청해야 이 로직이 작동함
    @GetMapping("/email-verifications")
    public ModelAndView verifyEmail(@RequestParam @Email String email,
                                    @RequestParam String verificationCode) {
        userService.verifyEmail(email, verificationCode);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post requestBody) {
        User user = userMapper.userPostDtoToUser(requestBody);
        User createdUser = userService.createUser(user);
        log.info("createdUser.username = " + createdUser.getUsername());
        return new ResponseEntity<>(userMapper.userToUserSimpleResponseDto(createdUser), HttpStatus.CREATED);

        // API 통신용
        //return new ResponseEntity<>(createSimpleResponseDto(), HttpStatus.CREATED);
    }

    /*
    @PostMapping("/{user-id}/profiles")
    public ResponseEntity postProfileImage(@PathVariable("user-id") @Positive Long userId,
                                           @RequestPart("file") MultipartFile multipartFile) {
        User userWithProfileImg = userService.addProfileImage(userId, multipartFile);
        return new ResponseEntity<>(userMapper.userToUserSimpleResponseDto(userWithProfileImg), HttpStatus.OK);

        // API 통신용
//        String result = "파일 업로드 성공";
//        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    */

    @PatchMapping(value = "/{user-id}"/*, consumes = {"multipart/form-data"}*/)
    public ResponseEntity patchUser(@PathVariable("user-id") @Positive Long userId,
                                    @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                    @RequestPart(value = "data", required = false) @Valid UserDto.Patch patchDto) {
        if (patchDto == null) {
            patchDto = new UserDto.Patch();
        }

        if (multipartFile != null) patchDto.setProfileImageUrl(fileUploadService.save(multipartFile));
        // 위 null 처리로 인해 file, data 둘 다 없이 요청 보내도 요청/응답되긴 함

        patchDto.setUserId(userId);
        User user = userService.updateUser(userMapper.userPatchDtoToUser(patchDto));
        return new ResponseEntity<>(userMapper.userToUserPatchResponseDto(user), HttpStatus.OK);

        // API 통신용
        //return new ResponseEntity<>(createUserPatchResponseDto(), HttpStatus.OK); // todo 회원 정보 수정 후 어떤 화면으로 연결/이동하지?
    }

    @DeleteMapping("/{user-id}/profiles")
    public ResponseEntity deleteProfileImage(@PathVariable("user-id") @Positive Long userId) {
        userService.deleteProfileImage(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 관리자가 처리하거나, 또는 특정 조건이 만족되었을 때에 이벤트 발생시켜 처리
    /*
    @PatchMapping("/reports/{user-id}")
    public ResponseEntity banUser(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        return new ResponseEntity<>(createUserBanResponseDto(), HttpStatus.OK);
    }
     */

    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") @Positive Long userId) {
        UserDto.UserDetailsDb userDetailsDb = userService.findUserDetails(userId);
        return new ResponseEntity<>(userDetailsDb, HttpStatus.OK);

        // API 통신용
        //return new ResponseEntity<>(createUserDetailResponseDto(), HttpStatus.OK);
    }

    /*
    @GetMapping("/{user-id}/habits/categories")
    public ResponseEntity getActiveCategories(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        List<UserDto.CategoryResponse> responseDtos = List.of(createCategoryResponseDto(), createCategoryResponseDto(), createCategoryResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
     */

    /* bookmark(습관 찜하기) 등록 = habit controller에 해당 요청 처리하는 핸들러 메서드 postBookmark() 있음
    userId 및 habitId로 요청,
    비로그인 회원은 로그인 페이지로 이동,
    해당 습관 제작자는 해당 버튼이 보이지 않음
     */
    // 회원이 찜한 습관들의 목록 출력
    @GetMapping("/{user-id}/bookmarks")
    public ResponseEntity getBookmarks(@PathVariable("user-id") @Positive Long userId,
                                       @RequestParam(required = false) @Positive Long lastId,
                                       @RequestParam @Positive int size) {
        List<Habit> habits = bookmarkService.findBookmarkHabits(lastId, userId, size);
        return new ResponseEntity<>(userMapper.habitsToUserDtoHabitResponses(habits), HttpStatus.OK);

        // API 통신용
//        List<HabitDto.Response> responses = List.of(habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto());
//        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{user-id}/habits/hosts")
    public ResponseEntity getHostHabits(@PathVariable("user-id") @Positive Long userId,
                                        @RequestParam(required = false) @Positive Long lastId,
                                        @RequestParam @Positive int size) {
        List<UserDto.HabitResponse> habitResponses = userService.findHostHabits(lastId, userId, size);
        return new ResponseEntity(habitResponses, HttpStatus.OK);

        // API 통신용
//        List<UserDto.HabitResponse> responseDtos = List.of(createHabitResponseDto(), createHabitResponseDto());
//        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{user-id}/habits/{habit-id}/certificates")
    public ResponseEntity getHabitCertificate(@PathVariable("habit-id") @Positive Long habit_id,
                                              @PathVariable("user-id") @Positive Long user_id) {
        UserDto.SuccessHabitCertificate successHabitCertificate = userService.issueHabitCertificate(user_id, habit_id);
        return new ResponseEntity(successHabitCertificate, HttpStatus.OK);

        // API 통신용
//        return new ResponseEntity<>(createSucessHabitCertificate(), HttpStatus.OK);
    }

    /* 2023.1.13(금) 15h10 habit controller가 처리하는 것이 맞음!
    @GetMapping("/challenges/success")
    public ResponseEntity getSuccessUsers(@Positive @RequestParam int page,
                                          @Positive @RequestParam int size) {
        // API 통신용
        List<UserDto.SimpleResponse> responseDtos = List.of(createUserSimpleResponseDto(), createUserSimpleResponseDto(), createUserSimpleResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
     */

    @GetMapping("/{user-id}/passwords/check")
    public ResponseEntity<Boolean> checkPasswordCorrect(@PathVariable("user-id") @Positive Long userId,
                                                        @Valid @RequestBody UserDto.CheckPassword requestBody) {
        requestBody.setUserId(userId);
        User user = userMapper.UserCheckPasswordDtoToUser(requestBody);

        return new ResponseEntity<>(userService.verifyExistPassword(user), HttpStatus.OK);
        // API 통신용
//        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") @Positive Long userId) {
        userService.quitUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/reissues")
//    public ResponseEntity reissueToken(@Validated UserDto.TokenRequest requestBody, HttpServletResponse response) throws ServletException, IOException {
//        userService.reissueToken(requestBody, response);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/{user-id}/logout")
    public ResponseEntity logout(@Validated @RequestBody UserDto.LogoutRequest requestBody) {
        userService.logout(requestBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
