package challenge.server.habit.controller;

import challenge.server.auth.entity.Auth;
import challenge.server.auth.mapper.AuthMapper;
import challenge.server.auth.service.AuthService;
import challenge.server.bookmark.entity.Bookmark;
import challenge.server.bookmark.mapper.BookmarkMapper;
import challenge.server.bookmark.service.BookmarkService;
import challenge.server.challenge.dto.ChallengeDto;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Api
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

    @PostMapping
    public ResponseEntity postHabit(@RequestPart("thumbImg") MultipartFile thumbImg,
                                    @RequestPart("succImg") MultipartFile succImg,
                                    @RequestPart("failImg") MultipartFile failImg,
                                    @RequestPart("data") @Valid HabitDto.Post habitPostDto,
                                    @RequestParam @Positive Long userId) { // TODO 로그인한 사용자 Id 쿼리파라미터 대신 내부에서 얻기
        // TODO 이미지 파일 리스트로 받기
        // TODO 아래 과정 컨트롤러 말고 DTO에서 처리하기
        Habit habit = habitMapper.habitPostDtoToHabit(habitPostDto);
        habit.setThumbImgUrl(fileUploadService.save(thumbImg));
        habit.setThumbImgUrl(fileUploadService.save(succImg));
        habit.setThumbImgUrl(fileUploadService.save(failImg));

        Habit createHabit = habitService.createHabit(habit);
        return new ResponseEntity(habitMapper.habitToHabitResponseDetailDto(createHabit, userId), HttpStatus.CREATED);
    }

    @PatchMapping("/{habit-id}")
    public ResponseEntity patchHabit(@RequestPart(value = "thumbImg", required = false) MultipartFile thumbImg,
                                     @RequestPart(value = "succImg", required = false) MultipartFile succImg,
                                     @RequestPart(value = "failImg", required = false) MultipartFile failImg,
                                     @RequestPart("data") @Valid HabitDto.Patch habitPatchDto,
                                     @PathVariable("habit-id") @Positive Long habitId,
                                     @RequestParam @Positive Long userId) {

        Habit habit = habitMapper.habitPatchDtoToHabit(habitPatchDto);
        if(thumbImg!=null) habit.setThumbImgUrl(fileUploadService.save(thumbImg));
        if(succImg!=null) habit.setThumbImgUrl(fileUploadService.save(succImg));
        if(failImg!=null) habit.setThumbImgUrl(fileUploadService.save(failImg));

        habit.setHabitId(habitId);
        Habit updateHabit = habitService.updateHabit(habit);
        return new ResponseEntity(habitMapper.habitToHabitResponseDetailDto(updateHabit,userId), HttpStatus.OK);
    }

    @DeleteMapping("/{habit-id}")
    public ResponseEntity deleteHabit(@PathVariable("habit-id") @Positive Long habitId) {
        habitService.deleteHabit(habitId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 습관 검색(첫 화면 모두 / 키워드 조회) - 응답 DTO
    @GetMapping("/search")
    public ResponseEntity getAllByKeyword(@RequestParam(required = false) String keyword,
                                          @RequestParam @Positive int page,
                                          @RequestParam @Positive int size) {
        List<Habit> habits;
        if(keyword==null) habits = habitService.findAll(page,size);
        else habits = habitService.findAllByKeyword(keyword,page,size);
        return new ResponseEntity(habitMapper.habitsToHabitResponseDtos(habits), HttpStatus.OK);
    }

    // 습관 검색(카테고리 조회) - 응답 DTO
    @GetMapping("/search/{category-id}")
    public ResponseEntity getAllByCategory(@PathVariable("category-id") Long categoryId,
                                           @RequestParam @Positive int page,
                                           @RequestParam @Positive int size) {
        List<Habit> habits = habitService.findAllByCategory(categoryId,page,size);
        return new ResponseEntity(habitMapper.habitsToHabitResponseDtos(habits), HttpStatus.OK);
    }

    @GetMapping("/{habit-id}")
    public ResponseEntity getHabit(@PathVariable("habit-id") @Positive Long habitId,
                                   @RequestParam @Positive Long userId) {
        Habit findHabit = habitService.findHabit(habitId);
        return new ResponseEntity(habitMapper.habitToHabitResponseDetailDto(findHabit, userId), HttpStatus.OK);
    }

    // 습관 조회 - 상세정보 탭 - 습관 시작하기 - Challenge DTO
    @PostMapping("/{habit-id}/challenges")
    public ResponseEntity postChallenge(@PathVariable("habit-id") @Positive Long habitId,
                                        @RequestParam @Positive Long userId) {
        ChallengeDto.Response responseDto = createChallengeResponseDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    // 습관 조회 - 상세정보 탭 - 습관 상태 변경
    @PatchMapping("/{habit-id}/challenges/{challenge-id}/status")
    public ResponseEntity changeChallengeStatus(@PathVariable("challenge-id") @Positive Long challengeId,
                                                @RequestParam("statusId") @Positive Long statusId) {
        ChallengeDto.Response responseDto = createChallengeResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 습관 조회 - 통계 탭 - 통계 DTO
    @GetMapping("/{habit-id}/statistics")
    public ResponseEntity getHabitByStatistics(@PathVariable("habit-id") @Positive Long habitId) {
        // TODO 통계 테이블 설정 후 추가 예정
        return new ResponseEntity(HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - Review 리스트 DTO
    @GetMapping("/{habit-id}/reviews")
    public ResponseEntity getReviewsByHabit(@PathVariable("habit-id") @Positive Long habitId,
                                            @RequestParam @Positive int page,
                                            @RequestParam @Positive int size) {
        List<Review> reviews = reviewService.findAllByHabit(habitId, page, size);
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

        return new ResponseEntity(reviewMapper.toDto(createReview), HttpStatus.CREATED);
    }

    // 습관 조회 - 후기 탭 - 후기 수정 - Review DTO
    @PatchMapping("/{habit-id}/reviews/{review-id}")
    public ResponseEntity patchReview(@PathVariable("review-id") @Positive Long reviewId,
                                      @RequestBody @Valid ReviewDto.Patch reviewPatchDto) {
        Review review = reviewMapper.toEntity(reviewPatchDto);
        review.setReviewId(reviewId);
        Review updateReview = reviewService.updateReview(review);
        return new ResponseEntity(reviewMapper.toDto(updateReview), HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - 후기 삭제
    @DeleteMapping("/{habit-id}/reviews/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive Long reviewId) {
        reviewService.deleteReview(reviewId);
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
                                          @RequestParam @Positive int page,
                                          @RequestParam @Positive int size) {
        List<Auth> auths = authService.findAllByHabit(habitId, page, size);
        return new ResponseEntity(authMapper.toDtos(auths), HttpStatus.OK);
    }

    // 습관 조회 - 인증 탭 - 인증 신고
    @PostMapping("/{habit-id}/auths/{auth-id}/reports")
    public ResponseEntity postAuthReport(@PathVariable("auth-id") @Positive Long authId,
                                         @RequestBody @Valid ReportDto.Post reportDto) {
        Report createReport = reportService.createReport(reportMapper.reportPostDtoToReport(reportDto));
        return new ResponseEntity(reportMapper.reportToReportResponseDto(createReport), HttpStatus.CREATED);
    }

    // 습관 북마크 - 북마크 등록 or 취소 메시지
    @PostMapping("/{habit-id}/bookmarks")
    public ResponseEntity postBookmark(@PathVariable("habit-id") @Positive Long habitId,
                                        @RequestParam @Positive Long userId) {
        Bookmark bookmark = bookmarkService.createBookmark(habitId, userId);
        return new ResponseEntity<>(bookmarkMapper.bookmarkToBookmarkResponseDto(bookmark), HttpStatus.CREATED);

        // API 통신용
//        String body = "즐겨찾기에 추가 되었습니다.";
//        return new ResponseEntity(body, HttpStatus.OK);
    }

    // 습관 북마크 - 북마크 취소 메시지
    @DeleteMapping("/{habit-id}/bookmarks")
    public ResponseEntity deleteBookmark(@PathVariable("habit-id") @Positive Long habitId,
                                       @RequestParam @Positive Long bookmarkId) {
        bookmarkService.deleteBookmark(bookmarkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // API 통신용
//        String body = "즐겨찾기에서 삭제 되었습니다.";
//        return new ResponseEntity(body, HttpStatus.NO_CONTENT);
    }

    // 습관 신고
    @PostMapping("/{habit-id}/reports")
    public ResponseEntity postHabitReport(@PathVariable("habit-id") @Positive Long habitId,
                                          @RequestBody @Valid ReportDto.Post reportDto) {
        Report createReport = reportService.createReport(reportMapper.reportPostDtoToReport(reportDto));
        return new ResponseEntity(reportMapper.reportToReportResponseDto(createReport), HttpStatus.CREATED);
    }

    // 응답 더미데이터 - 챌린지 DTO
    public ChallengeDto.Response createChallengeResponseDto(){
        return ChallengeDto.Response.builder()
                .challengeId(1L).challenger("username")
                .habitTitle("매일일기").status("CHALLENGE").build();
    }
}
