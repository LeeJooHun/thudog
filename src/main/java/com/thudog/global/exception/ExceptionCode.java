package com.thudog.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    DUPLICATE_USERNAME(4001, "이미 존재하는 사용자 이름입니다."),
    DUPLICATE_NICKNAME(4002, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_REVIEW_ID(4043, "요청한 ID에 해당하는 리뷰가 없습니다."),
    NOT_FOUND_USER_USERNAME(4044, "요청한 사용자 이름에 해당하는 사용자가 없습니다."),
    INVALID_PASSWORD(4045, "요청한 사용자 이름과 비밀번호가 일치하지 않습니다"),

    INVALID_INPUT(4000, "유효하지 않은 입력입니다.");

    private final int code;
    private final String message;
}