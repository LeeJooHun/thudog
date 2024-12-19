package com.thudog.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NicknameUpdateRequest {
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
