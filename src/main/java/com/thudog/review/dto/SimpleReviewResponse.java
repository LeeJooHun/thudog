package com.thudog.review.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SimpleReviewResponse {
    private String title;
    private String image;
    private double rating;
    private String isbn;
}
