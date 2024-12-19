package com.thudog.user.controller;

import com.thudog.user.dto.NicknameUpdateRequest;
import com.thudog.user.dto.UserResponse;
import com.thudog.user.dto.UserSignUpRequest;
import com.thudog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        UserResponse userResponse = userService.getUser(username);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignUpRequest UserSingUprequest) {
        Long userId = userService.signUp(UserSingUprequest);
        return ResponseEntity.created(URI.create("/api/users/" + userId)).build();
    }

    @PutMapping("/{username}/image")
    public ResponseEntity<Void> updateImage(@PathVariable String username, @RequestParam MultipartFile file) {
        userService.updateImage(username, file);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/nickname")
    public ResponseEntity<Void> updateNickname(@PathVariable String username, @RequestBody @Valid NicknameUpdateRequest nicknameUpdateRequest) {
        userService.updateNickname(username, nicknameUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}