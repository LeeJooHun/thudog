package com.thudog.user.controller;

import com.thudog.user.dto.UserSignUpRequest;
import com.thudog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignUpRequest request) {
        Long userId = userService.signUp(request);
        return ResponseEntity.created(URI.create("/api/users/" + userId)).build();
    }
}