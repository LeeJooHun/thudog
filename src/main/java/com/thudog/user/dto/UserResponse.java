package com.thudog.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private String nickname;
    private byte[] profileImage;
}
