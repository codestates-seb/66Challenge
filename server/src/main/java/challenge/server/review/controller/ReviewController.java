package challenge.server.review.controller;

import challenge.server.review.dto.ReviewDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Api
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @ApiOperation(value = "모든 리뷰 및 평점 조회")
    @GetMapping
    public ResponseEntity findAll(@RequestParam @Positive int page,
                                  @RequestParam @Positive int size) {
        List<ReviewDto.Response> responseDtos = List.of(createResponseDto(), createResponseDto2());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    public ReviewDto.Response createResponseDto() {
        return ReviewDto.Response.builder()
                .reviewId(1L)
                .reviewer("reviewer")
                .score(5)
                .body("최고의 경험이었어요!")
                .createdAt(LocalDateTime.now().toString())
                .build();
    }

    public ReviewDto.Response createResponseDto2() {
        return ReviewDto.Response.builder()
                .reviewId(2L)
                .reviewer("reviewer2")
                .score(1)
                .body("별로였어요")
                .createdAt(LocalDateTime.now().toString())
                .build();
    }
}
