package com.thudog.comment.domain;

import com.thudog.post.domain.Post;
import com.thudog.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 정보

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 게시글 정보

    @ManyToOne
    @JoinColumn(name = "root_id", nullable = true)
    private Comment root; // 최상위 부모 댓글 ID (최상위 댓글이 본인이면 null)

    @Column(name = "parent_id")
    private Long parentId;

    @Builder.Default
    @OneToMany(mappedBy = "root", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>(); // 자식 댓글

}
