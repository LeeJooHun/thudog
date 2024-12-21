package com.thudog.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "사용자 이름이 비어있습니다.")
    private String username;

    @NotNull(message = "본문 내용이 비어있습니다.")
    private List<Map<String, String>> contentList;

}
