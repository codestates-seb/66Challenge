package challenge.server.domain.challenge.controller;

import challenge.server.domain.auth.dto.AuthDto;
import challenge.server.domain.auth.entity.Auth;
import challenge.server.domain.auth.mapper.AuthMapper;
import challenge.server.domain.auth.service.AuthService;
import challenge.server.domain.challenge.dto.ChallengeDto;
import challenge.server.domain.challenge.service.ChallengeService;
import challenge.server.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static challenge.server.domain.challenge.entity.Challenge.Status.*;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;
    private final AuthService authService;
    private final FileUploadService fileUploadService;
    private final AuthMapper authMapper;

    @GetMapping("/users/{user-id}/challenge")
    public ResponseEntity findAllByChallenge(@PathVariable("user-id") @Positive Long userId,
                                             @RequestParam(required = false) @Positive Long lastId,
                                             @RequestParam @Positive int size) {
        List<ChallengeDto.Response> responses = challengeService.findAllByUserAndStatus(lastId, userId, CHALLENGE, size);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/users/{user-id}/success")
    public ResponseEntity findAllBySuccess(@PathVariable("user-id") @Positive Long userId,
                                           @RequestParam(required = false) @Positive Long lastId,
                                           @RequestParam @Positive int size) {
        List<ChallengeDto.Response> responses = challengeService.findAllByUserAndStatus(lastId, userId, SUCCESS, size);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/{chaellenge-id}/auths")
    public ResponseEntity createAuth(@PathVariable("chaellenge-id") @Positive Long challengeId,
                                     @RequestPart("file") MultipartFile multipartFile,
                                     @RequestPart("data") @Valid AuthDto.Post postDto) {
        challengeService.todayAuthCheck(challengeId);
        Auth auth = authMapper.toEntity(postDto);
        String authImageUrl = fileUploadService.save(multipartFile);
        auth.changeImageUrl(authImageUrl);

        return new ResponseEntity<>(authService.createAuth(auth, challengeId), HttpStatus.CREATED);
    }
}
