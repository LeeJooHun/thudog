package com.thudog.post.controller;

import com.thudog.post.dto.PostCreateRequest;
import com.thudog.post.dto.PostResponse;
import com.thudog.post.dto.PostSummaryResponse;
import com.thudog.post.dto.PostUpdateRequest;
import com.thudog.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostCreateRequest postCreateRequest) {
        Long postId = postService.createPost(postCreateRequest); // 생성된 postId 반환
        return ResponseEntity.created(URI.create("/api/posts/" + postId)).build();
    }

    @GetMapping
    public ResponseEntity<List<PostSummaryResponse>> getPosts() {
        List<PostSummaryResponse> postSummaryResponses = postService.getPosts();
        return ResponseEntity.ok(postSummaryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse postResponse = postService.getPost(id);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody @Valid PostUpdateRequest postUpdateRequest) {
        postService.updatePost(id, postUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}