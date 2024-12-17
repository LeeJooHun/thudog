package com.thudog.user.service;

import com.thudog.global.exception.BadRequestException;
import com.thudog.user.domain.User;
import com.thudog.user.domain.UserRepository;
import com.thudog.user.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.thudog.global.exception.ExceptionCode.DUPLICATE_NICKNAME;
import static com.thudog.global.exception.ExceptionCode.DUPLICATE_USERNAME;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public Long signUp(UserSignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException(DUPLICATE_USERNAME);
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new BadRequestException(DUPLICATE_NICKNAME);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .build();
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}
