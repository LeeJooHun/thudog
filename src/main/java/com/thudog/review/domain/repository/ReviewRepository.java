package com.thudog.review.domain.repository;

import com.thudog.review.domain.Review;
import com.thudog.user.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByIsbn(String isbn);
    List<Review> findAll(Sort sort);
    List<Review> findByUser(User user, Sort sort);
}
