package com.thudog.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryResponse {
    private Long id;
    private String title;
    private String nickname;
    private byte[] profileImage;
}
