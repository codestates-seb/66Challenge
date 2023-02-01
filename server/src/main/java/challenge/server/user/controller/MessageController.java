package challenge.server.user.controller;

import challenge.server.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    @PostMapping("/message")
    public ResponseEntity postMessage(@RequestBody UserDto.MessageRequest requestBody) {
        System.out.println(requestBody.getMessage());

        String text = "Hello, message";
        return new ResponseEntity<>(text, HttpStatus.OK);
    }

    @GetMapping("/message")
    public ResponseEntity getMessage(@RequestBody UserDto.MessageRequest requestBody) {
        String text = "Hello, message";
        return new ResponseEntity<>(text, HttpStatus.OK);
    }
}
