package com.thudog.login.service;

import com.thudog.global.exception.BadRequestException;
import com.thudog.login.dto.LoginRequest;
import com.thudog.user.domain.User;
import com.thudog.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.thudog.global.exception.ExceptionCode.INVALID_PASSWORD;
import static com.thudog.global.exception.ExceptionCode.NOT_FOUND_USER_USERNAME;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));

        if (user.getPassword().equals(request.getPassword())) {
            return user.getNickname();
        } else {
            throw new BadRequestException(INVALID_PASSWORD);
        }
    }
}
