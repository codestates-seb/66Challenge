package challenge.server.challenge.controller;

import challenge.server.auth.dto.AuthDto;
import challenge.server.challenge.dto.ChallengeDto;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.review.dto.ReviewDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Api
@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeRepository challengeRepository;

    @ApiOperation(value = "챌린지 생성", notes = "습관 시작하기 버튼을 클릭할 경우 생성됩니다.")
    @PostMapping
    public ResponseEntity createChallenge(@RequestParam @Positive Long userId,
                                          @RequestParam @Positive Long habitId) {
        ChallengeDto.Response responseDto = createResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "챌린지 상태 변경", notes = "CHALLENGE = 1 / SUCCESS = 2 / FAIL = 3")
    @PatchMapping("/{challenge-id}/status")
    public ResponseEntity changeChallengeStatus(@PathVariable("challenge-id") @Positive Long challengeId,
                                          @RequestParam("statusId") @Positive Long statusId) {
        ChallengeDto.Response responseDto2 = createResponseDto2();
        return new ResponseEntity<>(responseDto2, HttpStatus.OK);
    }

    @ApiOperation(value = "챌린지 조회")
    @GetMapping("/{challenge-id}")
    public ResponseEntity findChallenge(@PathVariable("challenge-id") @Positive Long challengeId) {
        ChallengeDto.Response responseDto2 = createResponseDto2();
        return new ResponseEntity<>(responseDto2, HttpStatus.OK);
    }

    @ApiOperation(value = "모든 챌린지 조회")
    @GetMapping
    public ResponseEntity findAll(@RequestParam @Positive int page,
                                  @RequestParam @Positive int size) {
        List<ChallengeDto.Response> responseDtos = List.of(createResponseDto(), createResponseDto2());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 상태의 모든 챌린지 조회", notes = "CHALLENGE = 1 / SUCCESS = 2 / FAIL = 3")
    @GetMapping("/status/challenge")
    public ResponseEntity findAllByStatus(@RequestParam @Positive int page,
                                          @RequestParam @Positive int size) {
        return new ResponseEntity<>(List.of(createResponseDto2(), createResponseDto2()), HttpStatus.OK);
    }

    @ApiOperation(value = "인증글 등록")
    @PostMapping("/{chaellenge-id}/auths")
    public ResponseEntity createAuth(@PathVariable("chaellenge-id") @Positive Long challengeId,
                                     @RequestBody @Valid AuthDto.Post postDto) {
        AuthDto.Response responseDto = createAuthResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "인증글 수정")
    @PatchMapping("/{challenge-id}/auths/{auth-id}")
    public ResponseEntity updateAuth(@PathVariable("auth-id") @Positive Long authId,
                                     @RequestBody @Valid AuthDto.Patch patchDto) {
        AuthDto.Response responseDto = createAuthResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "인증 조회")
    @GetMapping("/{challenge-id}/auths/{auth-id}")
    public ResponseEntity findAuth(@PathVariable("auth-id") @Positive Long authId) {
        AuthDto.Response responseDto = createAuthResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 챌린지의 모든 인증글 조회")
    @GetMapping("/{chaellenge-id}/auths")
    public ResponseEntity findAuthsByChallenge(@PathVariable("chaellenge-id") @Positive Long challengeId,
                                               @RequestParam @Positive int page,
                                               @RequestParam @Positive int size) {
        return new ResponseEntity<>(List.of(createAuthResponseDto(), createAuthResponseDto()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "인증글 삭제")
    @DeleteMapping("/{challenge-id}/auths/{auth-id}")
    public ResponseEntity deleteAuth(@PathVariable("auth-id") @Positive Long authId) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "wildcard 사용")
    @PostMapping("/{challenge-id}/wildcards")
    public ResponseEntity useWildcard(@PathVariable("challenge-id") @Positive Long challengeId,
                                      @RequestParam("statusId") @Positive Long statusId) {
        ChallengeDto.Response responseDto2 = createResponseDto2();
        return new ResponseEntity<>(responseDto2, HttpStatus.OK);
    }

    public ChallengeDto.Response createResponseDto() {
        return ChallengeDto.Response.builder().challengeId(1L)
                .challenger("challenger").habitTitle("habitTitle").status("CHALLENGE").build();
    }

    public ChallengeDto.Response createResponseDto2() {
        return ChallengeDto.Response.builder().challengeId(2L)
                .challenger("challenger").habitTitle("habitTitle").score(5).usedWildcard(1)
                .review("최고의 경험이었어요!").status("SUCCESS").authIds(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)).build();
    }

    public ReviewDto.Response createReviewResponseDto() {
        return ReviewDto.Response.builder()
                .reviewId(1L)
                .reviewer("reviewer")
                .score(5)
                .body("최고의 경험이었어요!")
                .createdAt(LocalDateTime.now().toString())
                .build();
    }

    public ReviewDto.Response createReviewResponseDto2() {
        return ReviewDto.Response.builder()
                .reviewId(2L)
                .reviewer("reviewer2")
                .score(1)
                .body("별로였어요")
                .createdAt(LocalDateTime.now().toString())
                .build();
    }

    public AuthDto.Response createAuthResponseDto() {
        return AuthDto.Response.builder().authId(1L).body("body")
                .createdAt(LocalDateTime.now().toString()).build();
    }
}
