package com.thudog.auth.controller;

import com.thudog.auth.dto.LoginRequest;
import com.thudog.auth.dto.LoginResponse;
import com.thudog.auth.service.AuthService;
import com.thudog.user.dto.UserSignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        Long userId = authService.signUp(userSignUpRequest);
        return ResponseEntity.created(URI.create("/api/users/" + userId)).build();
    }
}