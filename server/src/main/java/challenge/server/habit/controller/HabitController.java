package challenge.server.habit.controller;

import challenge.server.auth.dto.AuthDto;
import challenge.server.challenge.dto.ChallengeDto;
import challenge.server.habit.dto.HabitDto;
import challenge.server.report.dto.ReportDto;
import challenge.server.review.dto.ReviewDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "습관 등록")
    @PostMapping
    public ResponseEntity postHabit(@RequestBody @Valid HabitDto.Post habitPostDto) {
        HabitDto.ResponseDetail responseDto = createResponseDetailDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "습관 수정")
    public ResponseEntity patchHabit(@RequestBody @Valid HabitDto.Patch habitPatchDto,
            @PathVariable("habit-id") @Positive Long habitId) {

        HabitDto.ResponseDetail responseDto = createResponseDetailDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "습관 삭제")
    @DeleteMapping("/{habit-id}")
    public ResponseEntity deleteHabit(@PathVariable("habit-id") @Positive Long habitId) {

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "습관 상세 조회")
    @GetMapping("/{habit-id}")
    public ResponseEntity getHabit(@PathVariable("habit-id") @Positive Long habitId) {
        HabitDto.ResponseDetail responseDto = createResponseDetailDto();
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    // 습관 조회 - 상세정보 탭 - 습관 시작하기 - Challenge DTO
    @ApiOperation(value = "습관 참여하기(시작하기)")
    @PostMapping("/{habit-id}/challenges/{user-id}")
    public ResponseEntity postChallenge(@PathVariable("habit-id") @Positive Long habitId,
                                        @PathVariable("user-id") @Positive Long userId) {
        ChallengeDto.Response responseDto = createChallengeResponseDto();
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    // TODO 습관 포기하기 기능 유무

    // 습관 조회 - 통계 탭 - 통계 DTO
    @ApiOperation(value = "습관 조회", notes = "습관 통계 탭을 눌러 확인합니다.")
    @GetMapping("/{habit-id}/statistics")
    public ResponseEntity getHabitByStatistics(@PathVariable("habit-id") @Positive Long habitId) {
        // TODO 통계 테이블 설정 후 추가 예정
        return new ResponseEntity(HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - Review 리스트 DTO
    @ApiOperation(value = "습관 후기 전체 조회", notes = "습관 후기 탭을 눌러 확인합니다.")
    @GetMapping("/{habit-id}/reviews")
    public ResponseEntity getReviewsByHabit(@PathVariable("habit-id") @Positive Long habitId,
                                            @RequestParam @Positive int page,
                                            @RequestParam @Positive int size) {
        List<ReviewDto.Response> responses = List.of(createReviewResponseDto(),createReviewResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - 후기 등록 - Review DTO
    @ApiOperation(value="습관 후기 등록")
    @PostMapping("/{habit-id}/reviews")
    public ResponseEntity postReview(@PathVariable("habit-id") @Positive Long habitId,
                                     @RequestParam @Positive Long userId,
                                     @RequestBody @Valid ReviewDto.Post reviewPostDto) {
        ReviewDto.Response responseDto = createReviewResponseDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    // 습관 조회 - 후기 탭 - 후기 수정 - Review DTO
    @ApiOperation(value="습관 후기 수정")
    @PatchMapping("/{habit-id}/reviews/{review-id}")
    public ResponseEntity patchReview(@PathVariable("review-id") @Positive Long reviewId,
                                      @RequestBody @Valid ReviewDto.Patch reviewPatchDto) {
        ReviewDto.Response responseDto = createReviewResponseDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    // 습관 조회 - 후기 탭 - 후기 삭제
    @ApiOperation(value="습관 후기 삭제")
    @DeleteMapping("/{habit-id}/reviews/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive Long reviewId) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 습관 조회 - 후기 탭 - 후기 신고
    @ApiOperation(value="습관 후기 신고 등록")
    @PostMapping("/{habit-id}/reviews/{review-id}/reports")
    public ResponseEntity postReviewReport(@PathVariable("review-id") @Positive Long reviewId,
                                           @RequestBody @Valid ReportDto.Post reviewReportPostDto) {
        return new ResponseEntity(createReviewReportDto(), HttpStatus.OK);
    }

    // 습관 조회 - 인증 탭 - Auth 리스트 DTO(특정 습관 id에 해당하는)
    @ApiOperation(value="습관 인증 전체 조회")
    @GetMapping("/{habit-id}/auths")
    public ResponseEntity getAuthsByHabit(@PathVariable("habit-id") @Positive Long habitId,
                                          @RequestParam @Positive int page,
                                          @RequestParam @Positive int size) {
        List<AuthDto.Response> responses = List.of(createAuthResponseDto(), createAuthResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 조회 - 인증 탭 - 인증 등록 - Auth DTO
    @ApiOperation(value="습관 인증 등록")
    @PostMapping("/{habit-id}/auths")
    public ResponseEntity postAuth(@PathVariable("habit-id") @Positive Long habitId,
                                   @RequestParam @Positive Long challengeId,
                                   @RequestBody @Valid AuthDto.Post authPostDto) {
        AuthDto.Response responseDto = createAuthResponseDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    // 습관 조회 - 인증 탭 - 인증 수정 - Auth DTO
    @ApiOperation(value="습관 인증 수정")
    @PatchMapping("/{habit-id}/auths/{auth-id}")
    public ResponseEntity patchAuth(@PathVariable("auth-id") @Positive Long authId,
                                    @RequestBody @Valid AuthDto.Patch authPatchDto) {
        AuthDto.Response responseDto = createAuthResponseDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    // 습관 조회 - 인증 탭 - 인증 삭제
    @ApiOperation(value="습관 인증 삭제")
    @DeleteMapping("/{habit-id}/auths/{auth-id}")
    public ResponseEntity deleteAuth(@PathVariable("auth-id") @Positive Long authId) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 습관 조회 - 인증 탭 - 인증 신고
    @ApiOperation(value="습관 인증 신고 등록")
    @PostMapping("/{habit-id}/auths/{auth-id}/reports")
    public ResponseEntity postAuthReport(@PathVariable("auth-id") @Positive Long authId,
                                         @RequestBody @Valid AuthDto.Post AuthReportPostDto) {

        return new ResponseEntity(createAuthReportDto(), HttpStatus.OK);
    }


//    // 습관 검색(첫 화면 모두 조회) - 응답 DTO
//    @GetMapping("/search")
//    public ResponseEntity findAll(@RequestParam @Positive int page,
//                                  @RequestParam @Positive int size) {
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

    // 습관 검색(첫 화면 모두 / 키워드 조회) - 응답 DTO
    @ApiOperation(value="습관 검색", notes = "keyword를 작성해 검색하거나, 작성하지 않으면 습관 목록을 전체 조회합니다.")
    @GetMapping("/search")
    public ResponseEntity getAllByKeyword(@RequestParam(required = false) String keyword,
                                           @RequestParam @Positive int page,
                                           @RequestParam @Positive int size) {
        List<HabitDto.Response> responses = List.of(createResponseDto(),createResponseDto(),createResponseDto(),createResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 검색(카테고리 조회) - 응답 DTO
    @ApiOperation(value="습관 검색", notes = "category-id로 특정 카테고리의 습관을 검색합니다.")
    @GetMapping("/search/{category-id}")
    public ResponseEntity getAllByCategory(@PathVariable("category-id") Long categoryId,
                                            @RequestParam @Positive int page,
                                            @RequestParam @Positive int size) {
        List<HabitDto.Response> responses = List.of(createResponseDto(),createResponseDto(),createResponseDto(),createResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 북마크 - 북마크 등록 or 취소 메시지
    @ApiOperation(value="북마크")
    @PostMapping("/{habit-id}/bookmarks")
    public ResponseEntity postBookmark(@PathVariable("habit-id") @Positive Long habitId,
                                        @RequestParam @Positive Long userId) {
        String body = "즐겨찾기에 추가 되었습니다.";
        return new ResponseEntity(body,HttpStatus.OK);
    }

    // 습관 신고 - 신고 접수 완료 메시지
    @ApiOperation(value="습관 신고 등록")
    @PostMapping("/{habit-id}/reports")
    public ResponseEntity postHabitReport(@PathVariable("habit-id") @Positive Long habitId,
                                          @RequestBody @Valid ReportDto.Post habitReportPostDto) {
        return new ResponseEntity(createHabitReportDto(), HttpStatus.CREATED);
    }

    // 응답 더미데이터 - 습관 상세 DTO
    public HabitDto.ResponseDetail createResponseDetailDto() {
        return HabitDto.ResponseDetail.builder()
                .habitId(1L).userId(1L)
                .title("매일매일 일기 쓰기").subTitle("매일일기")
                .category("자기계발").body("매일매일 일기를 작성해서 훌륭한 어른이 됩시다.")
                .authType("카메라")
                .authStartTime("00:00").authEndTime("24:00")
                .score(4.7f).isBooked(true)
                .build();
    }

    // 응답 더미데이터 - 습관 DTO
    public HabitDto.Response createResponseDto() {
        return HabitDto.Response.builder()
                .habitId(1L)
                .title("매일매일 일기 쓰기")
                .body("매일매일 일기를 작성해서 훌륭한 어른이 됩시다.")
                .isBooked(true)
                .build();
    }

    // 응답 더미데이터 - 인증 DTO
    public AuthDto.Response createAuthResponseDto() {
        return AuthDto.Response.builder().authId(1L).body("오늘도 증말루다가 열심히 달렸다.")
                .createdAt(LocalDateTime.now().toString()).build();
    }

    // 응답 더미데이터 - 리뷰 DTO
    public ReviewDto.Response createReviewResponseDto() {
        return ReviewDto.Response.builder()
                .reviewId(1L)
                .reviewer("reviewer")
                .score(5)
                .body("최고의 경험이었어요!")
                .createdAt(LocalDateTime.now().toString())
                .build();
    }

    // 응답 더미데이터 - 챌린지 DTO
    public ChallengeDto.Response createChallengeResponseDto(){
        return ChallengeDto.Response.builder()
                .challengeId(1L).challenger("username")
                .habitTitle("매일일기").status("CHALLENGE").build();
    }

    // 응답 더미데이터 - Report DTO (Habit, Review, Auth)
    public ReportDto.Response createHabitReportDto() {
        return ReportDto.Response.builder().reportId(1L)
                .postType("Habit").postId(1L)
                .reporter(1L).reportType("ABUSE")
                .build();
    }

    public ReportDto.Response createReviewReportDto() {
        return ReportDto.Response.builder().reportId(1L)
                .postType("Review").postId(1L)
                .reporter(1L).reportType("ABUSE")
                .build();
    }

    public ReportDto.Response createAuthReportDto() {
        return ReportDto.Response.builder().reportId(1L)
                .postType("Auth").postId(1L)
                .reporter(1L).reportType("ABUSE")
                .build();
    }
}
