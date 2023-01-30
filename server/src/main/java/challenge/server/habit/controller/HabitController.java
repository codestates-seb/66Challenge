package challenge.server.habit.controller;

import challenge.server.auth.dto.AuthDto;
import challenge.server.auth.entity.Auth;
import challenge.server.auth.mapper.AuthMapper;
import challenge.server.auth.service.AuthService;
import challenge.server.bookmark.entity.Bookmark;
import challenge.server.bookmark.mapper.BookmarkMapper;
import challenge.server.bookmark.service.BookmarkService;
import challenge.server.challenge.dto.ChallengeDto;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.mapper.ChallengeMapper;
import challenge.server.challenge.service.ChallengeService;
import challenge.server.file.service.FileUploadService;
import challenge.server.habit.dto.HabitDto;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.mapper.HabitMapperImpl;
import challenge.server.habit.service.HabitService;
import challenge.server.report.dto.ReportDto;
import challenge.server.report.entity.Report;
import challenge.server.report.mapper.ReportMapperImpl;
import challenge.server.report.service.ReportService;
import challenge.server.review.dto.ReviewDto;
import challenge.server.review.entity.Review;
import challenge.server.review.mapper.ReviewMapper;
import challenge.server.review.service.ReviewService;
import challenge.server.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;
    private final HabitMapperImpl habitMapper;
    private final BookmarkService bookmarkService;
    private final BookmarkMapper bookmarkMapper;
    private final ReportService reportService;
    private final ReportMapperImpl reportMapper;
    private final AuthService authService;
    private final AuthMapper authMapper;
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final FileUploadService fileUploadService;
    private final UserService userService;
    private final ChallengeService challengeService;
    private final ChallengeMapper challengeMapper;

    @PostMapping
    public ResponseEntity postHabit(@RequestPart("thumbImg") MultipartFile thumbImg,
                                    @RequestPart("succImg") MultipartFile succImg,
                                    @RequestPart(value = "failImg", required = false) MultipartFile failImg,
                                    @RequestPart("data") @Valid HabitDto.Post habitPostDto) {
        // TODO 이미지 파일 리스트로 받기
        // TODO 아래 과정 컨트롤러 말고 DTO에서 처리하기
        Habit habit = habitMapper.habitPostDtoToHabit(habitPostDto);
        habit.setThumbImgUrl(fileUploadService.save(thumbImg));
        habit.setSuccImgUrl(fileUploadService.save(succImg));
        if(failImg!=null) habit.setFailImgUrl(fileUploadService.save(failImg));

        Habit createHabit = habitService.createHabit(habit);

        return new ResponseEntity(habitMapper.habitToHabitResponseDetailDto(createHabit, habitPostDto.getHostUserId()), HttpStatus.CREATED);
    }

    @PatchMapping("/{habit-id}")
    public ResponseEntity patchHabit(@RequestPart(value = "thumbImg", required = false) MultipartFile thumbImg,
                                     @RequestPart(value = "succImg", required = false) MultipartFile succImg,
                                     @RequestPart(value = "failImg", required = false) MultipartFile failImg,
                                     @RequestPart("data") @Valid HabitDto.Patch habitPatchDto,
                                     @PathVariable("habit-id") @Positive Long habitId,
                                     @RequestParam @Positive Long userId) {

        Habit habit = habitMapper.habitPatchDtoToHabit(habitPatchDto);
        if (thumbImg != null) habit.setThumbImgUrl(fileUploadService.save(thumbImg));
        if (succImg != null) habit.setThumbImgUrl(fileUploadService.save(succImg));
        if (failImg != null) habit.setThumbImgUrl(fileUploadService.save(failImg));

        habit.setHabitId(habitId);
        Habit updateHabit = habitService.updateHabit(habit);
        return new ResponseEntity(habitMapper.habitToHabitResponseDetailDto(updateHabit, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{habit-id}")
    public ResponseEntity deleteHabit(@PathVariable("habit-id") @Positive Long habitId) {
        habitService.deleteHabit(habitId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 습관 검색(첫 화면 모두 / 키워드 조회) - 응답 DTO
    @GetMapping("/search")
    public ResponseEntity getAllByKeyword(@RequestParam(required = false) @Positive Long lastId,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam @Positive int size,
                                          @RequestParam(required = false) @Positive Long userId) {
        List<Habit> habits;
        if (keyword == null) habits = habitService.findAll(lastId, size);
        else habits = habitService.findAllByKeyword(lastId, keyword, size);
        return new ResponseEntity(habitMapper.habitsToHabitResponseDtos(habits, userId), HttpStatus.OK);
    }

    // 습관 검색(카테고리 조회) - 응답 DTO
    @GetMapping("/search/{category-id}")
    public ResponseEntity getAllByCategory(@PathVariable("category-id") Long categoryId,
                                           @RequestParam(required = false) @Positive Long lastId,
                                           @RequestParam @Positive int size,
                                           @RequestParam(required = false) @Positive Long userId) {

        List<Habit> habits = habitService.findAllByCategory(lastId, categoryId, size);
        return new ResponseEntity(habitMapper.habitsToHabitResponseDtos(habits, userId), HttpStatus.OK);
    }

    @GetMapping("/sort/recommend")
    public ResponseEntity getAllByScore(@RequestParam @Positive int size,
                                        @RequestParam @Positive int page,
                                        @RequestParam(required = false) @Positive Long userId) {

        List<Habit> habits = habitService.findAllByScore(page, size);
        return new ResponseEntity(habitMapper.habitsToHabitResponseDtos(habits, userId), HttpStatus.OK);
    }

    @GetMapping("/sort/popularity")
    public ResponseEntity getAllByPopularity(@RequestParam @Positive int size,
                                             @RequestParam @Positive int page,
                                             @RequestParam(required = false) @Positive Long userId) {

        List<Habit> habits = habitService.findAllByPopularity(page, size);
        return new ResponseEntity(habitMapper.habitsToHabitResponseDtos(habits, userId), HttpStatus.OK);
    }

    @GetMapping("/sort/newest")
    public ResponseEntity getAllByNewest(@RequestParam @Positive int size,
                                         @RequestParam(required = false) @Positive Long lastId,
                                         @RequestParam(required = false) @Positive Long userId) {

        List<Habit> habits = habitService.findAllByNewest(lastId, size);
        return new ResponseEntity(habitMapper.habitsToHabitResponseDtos(habits, userId), HttpStatus.OK);
    }

    // 습관 조회 - 상세 조회
    @GetMapping("/{habit-id}")
    public ResponseEntity getHabit(@PathVariable("habit-id") @Positive Long habitId,
                                   @RequestParam(required = false) @Positive Long userId) {

        Habit findHabit = habitService.findHabit(habitId);
        return new ResponseEntity(habitMapper.habitToHabitResponseDetailDto(findHabit, userId), HttpStatus.OK);
    }

    // TODO Advanced :: 습관 시작,포기 challengeId로 통신하기 -> Habit ResponseDto에 challengeId 포함하기.
    // 습관 조회 - 상세정보 탭 - 습관 시작하기 - Challenge DTO
    @PostMapping("/{habit-id}/challenges")
    public ResponseEntity postChallenge(@PathVariable("habit-id") @Positive Long habitId,
                                        @RequestParam @Positive Long userId) {

        // TODO toEntity Mapper 생성
        Challenge challenge = Challenge.builder()
                .habit(habitService.findHabit(habitId))
                .user(userService.findUser(userId))
                .status(Challenge.Status.CHALLENGE)
                .build();
        Challenge createChallenge = challengeService.createChallenge(userId, habitId, challenge);

        return new ResponseEntity(challengeMapper.toDto(createChallenge), HttpStatus.CREATED);
    }

    // 습관 조회 - 상세정보 탭 - 습관 상태 변경
    @PatchMapping("/{habit-id}/challenges")
    public ResponseEntity changeChallengeStatus(@PathVariable("habit-id") @Positive Long habitId,
                                                @RequestParam @Positive Long userId,
                                                @RequestParam String status) {

        Challenge changeChallnge = challengeService.changeStatus(userId, habitId, status);
        return new ResponseEntity<>(challengeMapper.toDto(changeChallnge), HttpStatus.OK);
    }

    // 습관 조회 - 통계 탭 - 통계 DTO
    @GetMapping("/{habit-id}/statistics")
    public ResponseEntity getHabitStatistics(@PathVariable("habit-id") @Positive Long habitId) {
        Habit habit = habitService.findHabit(habitId);
        return new ResponseEntity(habitMapper.makeHabitStatistics(habit), HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - Review 리스트 DTO
    @GetMapping("/{habit-id}/reviews")
    public ResponseEntity getReviewsByHabit(@PathVariable("habit-id") @Positive Long habitId,
                                            @RequestParam(required = false) @Positive Long lastId,
                                            @RequestParam @Positive int size) {
        List<Review> reviews = reviewService.findAllByHabit(lastId, habitId, size);
        return new ResponseEntity(reviewMapper.toDtos(reviews), HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - 후기 등록 - Review DTO
    @PostMapping("/{habit-id}/reviews")
    public ResponseEntity postReview(@PathVariable("habit-id") @Positive Long habitId,
                                     @RequestParam @Positive Long userId, // 로그인한 유저
                                     @RequestBody @Valid ReviewDto.Post reviewPostDto) {
        Review review = reviewMapper.toEntity(reviewPostDto);
        review.setUser(userService.findUser(userId));
        review.setHabit(habitService.findHabit(habitId));
        Review createReview = reviewService.createReview(review);
        habitService.calcAvgScore(createReview.getHabit().getHabitId());

        return new ResponseEntity(reviewMapper.toDto(createReview), HttpStatus.CREATED);
    }

    // 습관 조회 - 후기 탭 - 후기 수정 - Review DTO
    @PatchMapping("/{habit-id}/reviews/{review-id}")
    public ResponseEntity patchReview(@PathVariable("review-id") @Positive Long reviewId,
                                      @RequestBody @Valid ReviewDto.Patch reviewPatchDto) {
        Review review = reviewMapper.toEntity(reviewPatchDto);
        review.setReviewId(reviewId);
        Review updateReview = reviewService.updateReview(review);
        habitService.calcAvgScore(updateReview.getHabit().getHabitId());

        return new ResponseEntity(reviewMapper.toDto(updateReview), HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - 후기 삭제
    @DeleteMapping("/{habit-id}/reviews/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive Long reviewId) {
        reviewService.deleteReview(reviewId);
        Review review = reviewService.findVerifiedReview(reviewId);
        habitService.calcAvgScore(review.getHabit().getHabitId());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 습관 조회 - 후기 탭 - 후기 신고
    @PostMapping("/{habit-id}/reviews/{review-id}/reports")
    public ResponseEntity postReviewReport(@PathVariable("review-id") @Positive Long reviewId,
                                           @RequestBody @Valid ReportDto.Post reportDto) {
        Report createReport = reportService.createReport(reportMapper.reportPostDtoToReport(reportDto));
        return new ResponseEntity(reportMapper.reportToReportResponseDto(createReport), HttpStatus.CREATED);
    }

    // 습관 조회 - 인증 탭 - Auth 리스트 DTO(특정 습관 id에 해당하는)
    @GetMapping("/{habit-id}/auths")
    public ResponseEntity getAuthsByHabit(@PathVariable("habit-id") @Positive Long habitId,
                                          @RequestParam(required = false) @Positive Long lastId,
                                          @RequestParam @Positive int size) {
        List<Auth> auths = authService.findAllByHabit(lastId, habitId, size);
        return new ResponseEntity(authMapper.toDtos(auths), HttpStatus.OK);
    }

    @PatchMapping("/{habit-id}/auths/{auth-id}")
    public ResponseEntity updateAuth(@PathVariable("auth-id") @Positive Long authId,
                                     @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                     @RequestPart("data") @Valid AuthDto.Patch patchDto) {
        Auth auth = Auth.builder().authId(authId).body(patchDto.getBody()).build();
        if (multipartFile != null) auth.changeImageUrl(fileUploadService.save(multipartFile));
        Auth updateAuth = authService.updateAuth(auth);

        return new ResponseEntity<>(authMapper.toDto(updateAuth), HttpStatus.OK);
    }

    @DeleteMapping("/{habit-id}/auths/{auth-id}")
    public ResponseEntity deleteAuth(@PathVariable("auth-id") @Positive Long authId) {
        authService.deleteAuth(authId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 습관 조회 - 인증 탭 - 인증 신고
    @PostMapping("/{habit-id}/auths/{auth-id}/reports")
    public ResponseEntity postAuthReport(@PathVariable("auth-id") @Positive Long authId,
                                         @RequestBody @Valid ReportDto.Post reportDto) {
        Report createReport = reportService.createReport(reportMapper.reportPostDtoToReport(reportDto));
        return new ResponseEntity(reportMapper.reportToReportResponseDto(createReport), HttpStatus.CREATED);
    }

    // 습관 신고
    @PostMapping("/{habit-id}/reports")
    public ResponseEntity postHabitReport(@PathVariable("habit-id") @Positive Long habitId,
                                          @RequestBody @Valid ReportDto.Post reportDto) {
        Report createReport = reportService.createReport(reportMapper.reportPostDtoToReport(reportDto));
        return new ResponseEntity(reportMapper.reportToReportResponseDto(createReport), HttpStatus.CREATED);
    }

    // 습관 북마크 - 북마크 등록
    @PostMapping("/{habit-id}/bookmarks")
    public ResponseEntity postBookmark(@PathVariable("habit-id") @Positive Long habitId,
                                       @RequestParam @Positive Long userId) {
        Bookmark bookmark = bookmarkService.createBookmark(habitId, userId);
        return new ResponseEntity<>(bookmarkMapper.bookmarkToBookmarkResponseDto(bookmark), HttpStatus.CREATED);
    }

    // 습관 북마크 - 북마크 취소
    @DeleteMapping("/{habit-id}/bookmarks")
    public ResponseEntity deleteBookmark(@PathVariable("habit-id") @Positive Long habitId,
                                         @RequestParam @Positive Long userId) {

        Bookmark findBookmark = bookmarkService.findBookmarkByUserAndHabit(userId, habitId);
        bookmarkService.deleteBookmark(findBookmark.getBookmarkId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 응답 더미데이터 - 챌린지 DTO
    public ChallengeDto.Response createChallengeResponseDto() {
        return ChallengeDto.Response.builder()
                .challengeId(1L).challenger("username")
                .habitTitle("매일일기").status("CHALLENGE").build();
    }
}
