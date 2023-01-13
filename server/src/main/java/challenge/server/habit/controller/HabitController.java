package challenge.server.habit.controller;

import challenge.server.auth.dto.AuthDto;
import challenge.server.habit.dto.HabitDto;
import challenge.server.review.dto.ReviewDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Api
@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {

    // 습관 등록 - 상세 응답 DTO
    @PostMapping
    public ResponseEntity posteHabit() {
        HabitDto.ResponseDetail responseDto = createResponseDetailDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    // 습관 수정 - 상세 응답 DTO
    @PatchMapping("/{habit-id}")
    public ResponseEntity patchHabit(@PathVariable("habit-id") @Positive Long habitId) {
        HabitDto.ResponseDetail responseDto = createResponseDetailDto();
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    // 습관 삭제 - 삭제 완료 메시지
    @DeleteMapping("/{habit-id}")
    public ResponseEntity deleteHabit(@PathVariable("habit-id") @Positive Long habitId) {

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 습관 조회 - 상세정보 탭 - 상세 응답 DTO
    @GetMapping("/{habit-id}")
    public ResponseEntity getHabit(@PathVariable("habit-id") @Positive Long habitId) {
        HabitDto.ResponseDetail responseDto = createResponseDetailDto();
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    // 습관 조회 - 통계 탭 - 통계 DTO
    @GetMapping("/{habit-id}/statistics")
    public ResponseEntity getHabitByStatistics(@PathVariable("habit-id") @Positive Long habitId) {
        // TODO 통계 테이블 설정 후 추가 예정
        return new ResponseEntity(HttpStatus.OK);
    }

    // 습관 조회 - 후기 탭 - Review 리스트 DTO(특정 습관id에 해당하는)
    @GetMapping("/{habit-id}/reviews")
    public ResponseEntity getReviewsByHabit(@PathVariable("habit-id") @Positive Long habitId) {
        List<ReviewDto.Response> responses = List.of(createReviewResponseDto(),createReviewResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 조회 - 인증 탭 - Auth 리스트 DTO(특정 습관 id에 해당하는)
    @GetMapping("/{habit-id}/auths")
    public ResponseEntity getAuthsByHabit(@PathVariable("habit-id") @Positive Long habitId) {
        List<AuthDto.Response> responses = List.of(createAuthResponseDto(), createAuthResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 조회 - 인증 등록 - 상세 응답 DTO(인증 등록 후 습관 상세페이지의 인증 탭으로 리다이렉트 한다는 전제 하 - 추후 수정 가능)
    @PostMapping("/{habit-id}/auths")
    public ResponseEntity postAuth(@PathVariable("habit-id") @Positive Long habitId) {
        List<AuthDto.Response> responses = List.of(createAuthResponseDto(), createAuthResponseDto());
        return new ResponseEntity(responses, HttpStatus.CREATED);
    }

//    // 습관 검색(첫 화면 모두 조회) - 응답 DTO
//    @GetMapping("/search")
//    public ResponseEntity findAll(@RequestParam @Positive int page,
//                                  @RequestParam @Positive int size) {
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

    // 습관 검색(첫 화면 모두 / 키워드 조회) - 응답 DTO
    @GetMapping("/search")
    public ResponseEntity getAllByKeyword(@RequestParam(required = false) String keyword,
                                           @RequestParam @Positive int page,
                                           @RequestParam @Positive int size) {
        // TODO 조회 방식에 따른 RequestParam 논의
        List<HabitDto.Response> responses = List.of(createResponseDto(),createResponseDto(),createResponseDto(),createResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 검색(카테고리 조회) - 응답 DTO
    @GetMapping("/search/{category-id}")
    public ResponseEntity getAllByCategory(@PathVariable("category-id") Long categoryId,
                                            @RequestParam @Positive int page,
                                            @RequestParam @Positive int size) {
        List<HabitDto.Response> responses = List.of(createResponseDto(),createResponseDto(),createResponseDto(),createResponseDto());
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    // 습관 북마크 - 북마크 등록 or 취소 메시지
    @PostMapping("/{habit-id}/bookmark")
    public ResponseEntity postBookmark(@PathVariable("habit-id") @Positive Long habitId,
                                        @RequestParam @Positive Long userId) {
        String body = "즐겨찾기에 추가 되었습니다.";
        return new ResponseEntity(body,HttpStatus.OK);
    }

    // 습관 신고 - 신고 접수 완료 메시지
    @PostMapping("/{habit-id}/report")
    public ResponseEntity postReportHabit(@PathVariable("habit-id") @Positive Long habitId,
                                          @RequestParam @Positive Long userId) {
        String body = "신고가 접수 되었습니다.";
        return new ResponseEntity(body, HttpStatus.CREATED);
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
        return AuthDto.Response.builder().authId(1L).body("body")
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
}
