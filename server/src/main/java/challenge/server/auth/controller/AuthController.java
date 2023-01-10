package challenge.server.auth.controller;

import challenge.server.auth.dto.AuthDto;
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
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController {

    @ApiOperation(value = "모든 인증글 조회")
    @GetMapping
    public ResponseEntity findAll(@RequestParam @Positive int page,
                                  @RequestParam @Positive int size) {
        List<AuthDto.Response> responseDtos = List.of(createResponseDto(), createResponseDto());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    public AuthDto.Response createResponseDto() {
        return AuthDto.Response.builder().authId(1L).body("body")
                .createdAt(LocalDateTime.now().toString()).build();
    }
}
