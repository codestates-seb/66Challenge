package challenge.server.user.controller;

import challenge.server.security.service.SecurityService;
import challenge.server.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/reissue")
@Validated
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenController {
    private final SecurityService securityService;

    @PostMapping
    public ResponseEntity reissueToken(@Valid @RequestBody UserDto.TokenRequest requestBody, HttpServletResponse response) throws ServletException, IOException {
        securityService.reissueToken(requestBody, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
