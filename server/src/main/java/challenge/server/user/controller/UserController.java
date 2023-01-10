package challenge.server.user.controller;

import challenge.server.user.dto.UserDto;
import challenge.server.user.entity.User;
import challenge.server.user.mapper.UserMapper;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post requestBody) {
        User user = userMapper.userPostDtoToUser(requestBody);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(userMapper.userToUserResponseDto(createdUser), HttpStatus.OK);
    }



}
