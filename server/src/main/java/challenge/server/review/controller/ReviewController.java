package challenge.server.review.controller;

import challenge.server.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping
    public ResponseEntity postReview(@RequestParam("challengeId") @Positive Long challengeId,
                                     @RequestBody ReviewDto.Post postDto) {
        ReviewDto.Response responseDto = createResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{review-id}")
    public ResponseEntity updateReview(@PathVariable("review-id") @Positive Long reviewId,
                                       @RequestBody ReviewDto.Patch patchDto) {
        ReviewDto.Response responseDto = createResponseDto2();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{review-id}")
    public ResponseEntity findReview(@PathVariable("review-id") @Positive Long reviewId) {
        ReviewDto.Response responseDto = createResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam @Positive int page,
                                  @RequestParam @Positive int size) {
        List<ReviewDto.Response> responseDtos = List.of(createResponseDto(), createResponseDto2());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") @Positive Long reviewId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ReviewDto.Response createResponseDto() {
        return ReviewDto.Response.builder()
                .reviewer("reviewer")
                .score(5)
                .body("최고의 경험이었어요!")
                .createdAt(LocalDateTime.now().toString())
                .build();
    }

    public ReviewDto.Response createResponseDto2() {
        return ReviewDto.Response.builder()
                .reviewer("reviewer2")
                .score(1)
                .body("별로였어요")
                .createdAt(LocalDateTime.now().toString())
                .build();
    }
}
