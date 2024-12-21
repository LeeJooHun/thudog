package com.thudog.post.domain;

import com.thudog.comment.domain.Comment;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 게시글 제목

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // user_id 컬럼으로 관계 설정
    private User user; // 작성자 정보

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "content_list", joinColumns = @JoinColumn(name = "post_id"))
    private List<ContentItem> contentList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 댓글 리스트
}
