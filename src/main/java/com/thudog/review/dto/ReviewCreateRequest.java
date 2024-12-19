package com.thudog.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewCreateRequest {

    @NotBlank(message = "사용자 이름이 비어있습니다.")
    private String username;

    @NotBlank(message = "ISBN이 비어있습니다.")
    private String isbn;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String content;

    @NotNull(message = "유효한 범위의 별점을 입력해주세요.") // null 값 방지
    @DecimalMin(value = "0.0", inclusive = false, message = "별점은 0보다 커야 합니다.")
    @DecimalMax(value = "5.0", inclusive = true, message = "별점은 5 이하이어야 합니다.")
    private Double rating;

    private String title;

    private String image;
}
