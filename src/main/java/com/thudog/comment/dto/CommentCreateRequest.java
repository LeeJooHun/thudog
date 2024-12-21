package com.thudog.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank(message = "사용자 이름이 비어있습니다.")
    private String username;

    @NotBlank(message = "댓글 내용이 비어있습니다.")
    private String content;

    private Long parentId;

}
