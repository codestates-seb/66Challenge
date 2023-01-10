package challenge.server.auth.controller;

import challenge.server.auth.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping
    public ResponseEntity createAuth(@RequestParam("challengeId") Long challengeId,
                                     @RequestBody @Valid AuthDto.Post postDto) {
        AuthDto.Response responseDto = createResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{auth-id}")
    public ResponseEntity updateAuth(@PathVariable("auth-id") @Positive Long authId,
                                     @RequestBody @Valid AuthDto.Patch patchDto) {
        AuthDto.Response responseDto = createResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{auth-id}")
    public ResponseEntity findAuth(@PathVariable("auth-id") @Positive Long authId) {
        AuthDto.Response responseDto = createResponseDto();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam @Positive int page,
                                  @RequestParam @Positive int size) {
        List<AuthDto.Response> responseDtos = List.of(createResponseDto(), createResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{auth-id}")
    public ResponseEntity deleteAuth(@PathVariable("auth-id") @Positive Long authId) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public AuthDto.Response createResponseDto() {
        return AuthDto.Response.builder().authId(1L).body("body")
                .createdAt(LocalDateTime.now().toString()).build();
    }
}
