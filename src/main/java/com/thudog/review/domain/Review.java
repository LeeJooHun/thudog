package com.thudog.review.domain;

import com.thudog.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Getter
@Setter
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private double rating;

    @Column
    private LocalDate date;

    @Column
    private String title;

    @Column
    private String image;

    @Column
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }
}