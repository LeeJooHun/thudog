package com.thudog.comment.controller;

import com.thudog.comment.dto.CommentCreateRequest;
import com.thudog.comment.dto.CommentResponse;
import com.thudog.comment.dto.CommentUpdateRequest;
import com.thudog.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@PathVariable Long postId, @RequestBody @Valid CommentCreateRequest commentCreateRequest) {
        Long commentId = commentService.createComment(postId, commentCreateRequest);
        return ResponseEntity.created(URI.create("/api/posts/comments/" + commentId)).build();
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> commentResponses = commentService.getComments(postId);
        return ResponseEntity.ok(commentResponses);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {
        commentService.updateComment(commentId, commentUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}