package challenge.server.challenge.controller;

import challenge.server.auth.dto.AuthDto;
import challenge.server.auth.entity.Auth;
import challenge.server.auth.mapper.AuthMapper;
import challenge.server.auth.service.AuthService;
import challenge.server.challenge.dto.ChallengeDto;
import challenge.server.challenge.entity.Challenge;
import challenge.server.challenge.entity.Wildcard;
import challenge.server.challenge.mapper.ChallengeMapper;
import challenge.server.challenge.repository.ChallengeRepository;
import challenge.server.challenge.service.ChallengeService;
import challenge.server.challenge.service.WildcardService;
import challenge.server.file.service.FileUploadService;
import challenge.server.review.dto.ReviewDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static challenge.server.challenge.entity.Challenge.Status.*;

@Api
@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;
    private final AuthService authService;
    private final WildcardService wildcardService;
    private final FileUploadService fileUploadService;
    private final ChallengeMapper mapper;
    private final AuthMapper authMapper;

//    @ApiOperation(value = "챌린지 조회")
//    @GetMapping("/{challenge-id}")
//    public ResponseEntity findChallenge(@PathVariable("challenge-id") @Positive Long challengeId) {
//        Challenge challenge = challengeService.findChallenge(challengeId);
//
//        return new ResponseEntity<>(mapper.toDto(challenge), HttpStatus.OK);
//    }

    @GetMapping("/users/{user-id}/challenge")
    public ResponseEntity findAllByChallenge(@PathVariable("user-id") @Positive Long userId,
                                        @RequestParam @Positive int page,
                                        @RequestParam @Positive int size) {
        List<Challenge> challenges = challengeService.findAllByUserAndStatus(userId, CHALLENGE, page, size);

        return new ResponseEntity<>(mapper.toDtos(challenges), HttpStatus.OK);
    }

    @GetMapping("/users/{user-id}/success")
    public ResponseEntity findAllBySuccess(@PathVariable("user-id") @Positive Long userId,
                                        @RequestParam @Positive int page,
                                        @RequestParam @Positive int size) {
        List<Challenge> challenges = challengeService.findAllByUserAndStatus(userId, SUCCESS, page, size);

        return new ResponseEntity<>(mapper.toDtos(challenges), HttpStatus.OK);
    }

//    @ApiOperation(value = "모든 챌린지 조회")
//    @GetMapping
//    public ResponseEntity findAll(@RequestParam @Positive int page,
//                                  @RequestParam @Positive int size) {
//        List<Challenge> findAll = challengeService.findAll(page, size);
//
//        return new ResponseEntity<>(mapper.toDtos(findAll), HttpStatus.OK);
//    }

//    @ApiOperation(value = "도전중인 모든 챌린지 조회")
//    @GetMapping("/challenge")
//    public ResponseEntity findAllByChallenge(@RequestParam @Positive int page,
//                                          @RequestParam @Positive int size) {
//        List<Challenge> findAllByChallenge = challengeService.findAllStatus(CHALLENGE, page, size);
//
//        return new ResponseEntity<>(mapper.toDtos(findAllByChallenge), HttpStatus.OK);
//    }

//    @ApiOperation(value = "성공한 모든 챌린지 조회")
//    @GetMapping("/success")
//    public ResponseEntity findAllBySuccess(@RequestParam @Positive int page,
//                                        @RequestParam @Positive int size) {
//        List<Challenge> findAllBySuccess = challengeService.findAllStatus(SUCCESS, page, size);
//
//        return new ResponseEntity<>(mapper.toDtos(findAllBySuccess), HttpStatus.OK);
//    }

//    @ApiOperation(value = "실패한 모든 챌린지 조회")
//    @GetMapping("/fail")
//    public ResponseEntity findAllByFail(@RequestParam @Positive int page,
//                                          @RequestParam @Positive int size) {
//        List<Challenge> findAllByFail = challengeService.findAllStatus(FAIL, page, size);
//
//        return new ResponseEntity<>(mapper.toDtos(findAllByFail), HttpStatus.OK);
//    }

    @PostMapping("/{chaellenge-id}/auths")
    public ResponseEntity createAuth(@PathVariable("chaellenge-id") @Positive Long challengeId,
                                     @RequestPart("file") MultipartFile multipartFile,
                                     @RequestPart("data") @Valid AuthDto.Post postDto) {
        Challenge challenge = challengeService.findChallenge(challengeId);
        challenge.todayAuthCheck(LocalDateTime.now());
        Auth auth = authMapper.toEntity(postDto);
        String authImageUrl = fileUploadService.save(multipartFile);
        auth.changeImageUrl(authImageUrl);
        Auth createAuth = authService.createAuth(auth, challenge);

        return new ResponseEntity<>(authMapper.toDto(createAuth), HttpStatus.CREATED);
    }

//    @ApiOperation(value = "인증글 조회")
//    @GetMapping("/{challenge-id}/auths/{auth-id}")
//    public ResponseEntity findAuth(@PathVariable("auth-id") @Positive Long authId) {
//        Auth auth = authService.findAuth(authId);
//
//        return new ResponseEntity<>(authMapper.toDto(auth), HttpStatus.OK);
//    }

//    @ApiOperation(value = "특정 챌린지의 모든 인증글 조회")
//    @GetMapping("/{chaellenge-id}/auths")
//    public ResponseEntity findAuthsByChallenge(@PathVariable("chaellenge-id") @Positive Long challengeId,
//                                               @RequestParam @Positive int page,
//                                               @RequestParam @Positive int size) {
//        List<Auth> findAuths = authService.findAllByChallenge(challengeId, page, size); // 쿼리문 비교1
//        challengeService.findAuthsByChallengeId(challengeId);   // 쿼리문 비교2
//
//        return new ResponseEntity<>(authMapper.toDtos(findAuths), HttpStatus.CREATED);
//    }


//    @ApiOperation(value = "특정 챌린지의 모든 와일드카드 조회")
//    @GetMapping("/{challenge-id}/wildcards")
//    public ResponseEntity findWildcardsByChallenge(@PathVariable("challenge-id") @Positive Long challengeId) {
//        List<Wildcard> wildcards = wildcardService.findAllByChallenge(challengeId);
//
//        return new ResponseEntity<>(mapper.toWildcardDtos(wildcards), HttpStatus.OK);
//    }
}
