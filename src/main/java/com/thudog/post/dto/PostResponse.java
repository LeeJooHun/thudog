package com.thudog.post.dto;

import com.thudog.post.domain.ContentItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostResponse {
    private String title;
    private List<ContentItem> contentList;
    private String nickname;
    private byte[] profileImage;
}
