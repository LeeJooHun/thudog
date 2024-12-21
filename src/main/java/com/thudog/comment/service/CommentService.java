package com.thudog.comment.service;

import com.thudog.comment.domain.Comment;
import com.thudog.comment.domain.repository.CommentRepository;
import com.thudog.comment.dto.CommentCreateRequest;
import com.thudog.comment.dto.CommentResponse;
import com.thudog.comment.dto.CommentUpdateRequest;
import com.thudog.global.exception.BadRequestException;
import com.thudog.post.domain.Post;
import com.thudog.post.domain.repository.PostRepository;
import com.thudog.user.domain.User;
import com.thudog.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.thudog.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Long createComment(Long postId, CommentCreateRequest commentCreateRequest) {
        User user = userRepository.findByUsername(commentCreateRequest.getUsername())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_POST_ID));

        // 부모 댓글 설정 (null일 수 있음)
        Comment rootComment = null;
        Comment parentComment = null;
        if (commentCreateRequest.getParentId() != null) {
            parentComment = commentRepository.findById(commentCreateRequest.getParentId())
                    .orElseThrow(() -> new BadRequestException(NOT_FOUND_POST_ID));
            rootComment = parentComment.getRoot();
            if (rootComment == null) {
                rootComment = parentComment;
            }
        }

        Comment comment = Comment.builder()
                .content(commentCreateRequest.getContent())
                .user(user)
                .post(post)
                .root(rootComment)
                .parentId(commentCreateRequest.getParentId())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }


    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_POST_ID));

        List<Comment> comments = post.getComments()
                .stream()
                .filter(comment -> comment.getParentId() == null) // 부모 댓글만 필터링
                .sorted(Comparator.comparing(Comment::getId)) // ID 기준 정렬
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponseDto = CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .nickname(comment.getUser().getNickname())
                    .profileImage(comment.getUser().getProfileImage())
                    .replies(getReplies(comment))
                    .build();
            commentResponses.add(commentResponseDto);
        }
        return commentResponses;
    }

    private List<CommentResponse> getReplies(Comment parentComment) {
        List<CommentResponse> replies = new ArrayList<>();

        for (Comment reply : parentComment.getReplies()) {
            CommentResponse replyDto = CommentResponse.builder()
                    .id(reply.getId())
                    .parentId(reply.getParentId())
                    .content(reply.getContent())
                    .nickname(reply.getUser().getNickname())
                    .profileImage(reply.getUser().getProfileImage())
                    .build();
            replies.add(replyDto);
        }
        return replies;
    }

    public void updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_COMMENT_ID));

        comment.setContent(commentUpdateRequest.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_COMMENT_ID));

        commentRepository.delete(comment);
    }
}