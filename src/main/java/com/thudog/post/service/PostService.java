package com.thudog.post.service;

import com.thudog.global.exception.BadRequestException;
import com.thudog.post.domain.ContentItem;
import com.thudog.post.domain.Post;
import com.thudog.post.domain.repository.PostRepository;
import com.thudog.post.dto.PostCreateRequest;
import com.thudog.post.dto.PostResponse;
import com.thudog.post.dto.PostSummaryResponse;
import com.thudog.post.dto.PostUpdateRequest;
import com.thudog.user.domain.User;
import com.thudog.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.thudog.global.exception.ExceptionCode.NOT_FOUND_POST_ID;
import static com.thudog.global.exception.ExceptionCode.NOT_FOUND_USER_USERNAME;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(PostCreateRequest postCreateRequest) {
        User user = userRepository.findByUsername(postCreateRequest.getUsername())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));

        List<ContentItem> contentItems = postCreateRequest.getContentList().stream()
                .map(item -> createContentItem(item))
                .collect(Collectors.toList());

        Post post = Post.builder()
                .title(postCreateRequest.getTitle())
                .user(user)
                .contentList(contentItems)
                .build();

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    private ContentItem createContentItem(Map<String, String> item) {
        String type = item.get("type");
        return ContentItem.builder()
                .type(type)
                .imageData("image".equals(type) ? Base64.getDecoder().decode(item.get("data")) : null)
                .textData("text".equals(type) ? item.get("data") : null)
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> PostSummaryResponse.builder()
                        .id(post.getId())
                        .nickname(post.getUser().getNickname())
                        .profileImage(post.getUser().getProfileImage())
                        .title(post.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_POST_ID));

        PostResponse postResponse = PostResponse.builder()
                .title(post.getTitle())
                .contentList(post.getContentList())
                .nickname(post.getUser().getNickname())
                .profileImage(post.getUser().getProfileImage())
                .build();
        return postResponse;
    }

    public void updatePost(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_POST_ID));

        List<ContentItem> contentItems = postUpdateRequest.getContentList().stream()
                .map(item -> createContentItem(item))
                .collect(Collectors.toList());

        post.setTitle(postUpdateRequest.getTitle());
        post.setContentList(contentItems);
        postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_POST_ID));

        postRepository.delete(post);
    }


}
