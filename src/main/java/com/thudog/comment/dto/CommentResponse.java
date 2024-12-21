package com.thudog.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CommentResponse {
    private Long id;
    private Long parentId;
    private String content;
    private String nickname;
    private byte[] profileImage;
    private List<CommentResponse> replies; // 자식 댓글 리스트
}
