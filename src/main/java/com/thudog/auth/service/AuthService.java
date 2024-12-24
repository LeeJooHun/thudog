package com.thudog.auth.service;

import com.thudog.auth.dto.LoginResponse;
import com.thudog.global.exception.BadRequestException;
import com.thudog.auth.dto.LoginRequest;
import com.thudog.jwt.JwtUtil;
import com.thudog.user.domain.User;
import com.thudog.user.domain.repository.UserRepository;
import com.thudog.user.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.thudog.global.exception.ExceptionCode.*;
import static com.thudog.global.exception.ExceptionCode.DUPLICATE_NICKNAME;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            LoginResponse loginResponse = LoginResponse.builder()
                    .nickname(user.getNickname())
                    .token(token)
                    .build();
            return loginResponse;
        } else {
            throw new BadRequestException(INVALID_PASSWORD);
        }
    }

    public Long signUp(UserSignUpRequest UserSingUprequest) {
        if (checkDuplicateUsername(UserSingUprequest.getUsername())) {
            throw new BadRequestException(DUPLICATE_USERNAME);
        }

        if (checkDuplicateNickname(UserSingUprequest.getNickname())) {
            throw new BadRequestException(DUPLICATE_NICKNAME);
        }

        User user = User.builder()
                .username(UserSingUprequest.getUsername())
                .password(passwordEncoder.encode(UserSingUprequest.getPassword()))
                .nickname(UserSingUprequest.getNickname())
                .build();
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public boolean checkDuplicateUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean checkDuplicateNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }
}
