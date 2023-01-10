package challenge.server.challenge.controller;

import challenge.server.challenge.dto.ChallengeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    @PostMapping
    public ResponseEntity createChallenge(@RequestParam @Positive Long userId,
                                          @RequestParam @Positive Long habitId) {
        ChallengeDto.Response responseDto = createResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{challenge-id}")
    public ResponseEntity updateChallenge(@PathVariable("challenge-id") @Positive Long challengeId) {
        ChallengeDto.Response responseDto2 = createResponseDto2();
        return new ResponseEntity<>(responseDto2, HttpStatus.OK);
    }

    @GetMapping("/{challenge-id}")
    public ResponseEntity findChallenge(@PathVariable("challenge-id") @Positive Long challengeId) {
        ChallengeDto.Response responseDto2 = createResponseDto2();
        return new ResponseEntity<>(responseDto2, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam @Positive int page,
                                  @RequestParam @Positive int size) {
        List<ChallengeDto.Response> responseDtos = List.of(createResponseDto(), createResponseDto2());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{challenge-id}")
    public ResponseEntity deleteChallenge(@PathVariable("challenge-id") @Positive Long challengeId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ChallengeDto.Response createResponseDto() {
        return ChallengeDto.Response.builder()
                .challenger("challenger").habitTitle("habitTitle").status("CHALLENGE").build();
    }

    public ChallengeDto.Response createResponseDto2() {
        return ChallengeDto.Response.builder()
                .challenger("challenger").habitTitle("habitTitle").score(5).usedWildcard(2)
                .review("최고의 경험이었어요!").status("SUCCESS").authIds(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)).build();
    }
}
