package com.thudog.user.service;

import com.thudog.global.exception.BadRequestException;
import com.thudog.user.domain.User;
import com.thudog.user.domain.repository.UserRepository;
import com.thudog.user.dto.NicknameUpdateRequest;
import com.thudog.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.thudog.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void updateImage(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));

        try {
            byte[] imageData = file.getBytes();
            user.setProfileImage(imageData);
            userRepository.save(user);
        } catch (IOException e) {
            throw new BadRequestException(IMAGE_PROCESSING_ERROR);
        }
    }

    public void updateNickname(String username, NicknameUpdateRequest nicknameUpdateRequest) {
        if (checkDuplicateNickname(nicknameUpdateRequest.getNickname())) {
            throw new BadRequestException(DUPLICATE_NICKNAME);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));
        user.setNickname(nicknameUpdateRequest.getNickname());
        userRepository.save(user);
    }



    public boolean checkDuplicateNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));
        UserResponse userResponse = UserResponse.builder()
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .build();
        return userResponse;
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));
        userRepository.delete(user);
    }
}
