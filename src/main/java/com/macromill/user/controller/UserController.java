package com.macromill.user.controller;

import com.macromill.user.Utils;
import com.macromill.user.dto.UserRequestDto;
import com.macromill.user.dto.UserResponseDto;
import com.macromill.user.service.UserService;
import com.macromill.user.validation.CreateUser;
import com.macromill.user.validation.UpdateUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Validated
public class UserController {
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Validated(CreateUser.class) @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.postUser(userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<UserResponseDto> getUser(HttpServletRequest request, @PathVariable String userId) {
        ImmutablePair<String, String> credentials = Utils.getAuthorizationToken(request);
        UserResponseDto userResponseDto = userService.getUser(userId, credentials);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> updateUer(
            HttpServletRequest request,
            @PathVariable @NotNull(message = "user_id cannot be empty") String userId,
            @Validated(UpdateUser.class) @RequestBody UserRequestDto userRequestDto
    ) {
        ImmutablePair<String, String> credentials = Utils.getAuthorizationToken(request);
        UserResponseDto userResponseDto = userService.patchUser(userId, userRequestDto, credentials);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/close")
    public ResponseEntity<UserResponseDto> deleteUser(HttpServletRequest request) {
        ImmutablePair<String, String> credentials = Utils.getAuthorizationToken(request);
        UserResponseDto userResponseDto = userService.deleteUser(credentials);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }
}
