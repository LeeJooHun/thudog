package com.thudog.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
public class LoginResponse {
    private String nickname;
    private String token;
}
