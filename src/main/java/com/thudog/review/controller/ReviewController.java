package com.thudog.review.controller;

import com.thudog.review.dto.ReviewCreateRequest;
import com.thudog.review.dto.ReviewResponse;
import com.thudog.review.dto.ReviewUpdateRequest;
import com.thudog.review.dto.SimpleReviewResponse;
import com.thudog.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewCreateRequest reviewCreateRequest) {
        Long reviewId = reviewService.createReview(reviewCreateRequest);
        return ResponseEntity.created(URI.create("/api/reviews/" + reviewId)).build();
    }

    @GetMapping
    public ResponseEntity<List<SimpleReviewResponse>> getAllReviews() {
        List<SimpleReviewResponse> simpleReviewResponseDtos = reviewService.getAllReviews();
        return ResponseEntity.ok(simpleReviewResponseDtos);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<SimpleReviewResponse>> getMyReviews(@PathVariable String username) {
        List<SimpleReviewResponse> simpleReviewResponseDtos = reviewService.getMyReviews(username);
        return ResponseEntity.ok(simpleReviewResponseDtos);
    }


    @GetMapping("/{isbn}")
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable String isbn) {
        List<ReviewResponse> reviewResponseDtos = reviewService.getReviews(isbn);
        return ResponseEntity.ok(reviewResponseDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewUpdateRequest reviewUpdateRequest) {
        reviewService.updateReview(id, reviewUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}