package com.thudog.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
    private Long id;
    private String nickname;
    private String content;
    private double rating;
    private byte[] profileImage;
}
