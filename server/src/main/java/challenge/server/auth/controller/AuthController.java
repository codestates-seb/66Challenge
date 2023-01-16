package challenge.server.auth.controller;

import challenge.server.auth.dto.AuthDto;
import challenge.server.auth.entity.Auth;
import challenge.server.auth.mapper.AuthMapper;
import challenge.server.auth.service.AuthService;
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

    private final AuthService authService;
    private final AuthMapper mapper;

    @ApiOperation(value = "모든 인증글 조회")
    @GetMapping
    public ResponseEntity findAll(@RequestParam @Positive int page,
                                  @RequestParam @Positive int size) {
        List<Auth> findAll = authService.findAll(page, size);

        return new ResponseEntity<>(mapper.toDtos(findAll), HttpStatus.OK);
    }
}
