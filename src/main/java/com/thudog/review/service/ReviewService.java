package com.thudog.review.service;

import com.thudog.global.exception.BadRequestException;
import com.thudog.review.domain.Review;
import com.thudog.review.domain.repository.ReviewRepository;
import com.thudog.review.dto.ReviewCreateRequest;
import com.thudog.review.dto.ReviewResponse;
import com.thudog.review.dto.ReviewUpdateRequest;
import com.thudog.review.dto.SimpleReviewResponse;
import com.thudog.user.domain.User;
import com.thudog.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.thudog.global.exception.ExceptionCode.NOT_FOUND_REVIEW_ID;
import static com.thudog.global.exception.ExceptionCode.NOT_FOUND_USER_USERNAME;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public Long createReview(ReviewCreateRequest reviewCreateRequest) {
        User user = userRepository.findByUsername(reviewCreateRequest.getUsername())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));

        Review review = Review.builder()
                .content(reviewCreateRequest.getContent())
                .rating(reviewCreateRequest.getRating())
                .title(reviewCreateRequest.getTitle())
                .image(reviewCreateRequest.getImage())
                .isbn(reviewCreateRequest.getIsbn())
                .user(user)
                .build();

        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();
    }

    @Transactional(readOnly = true)
    public List<SimpleReviewResponse> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return reviews.stream()
                .map(review -> SimpleReviewResponse.builder()
                        .title(review.getTitle())
                        .rating(review.getRating())
                        .image(review.getImage())
                        .isbn(review.getIsbn())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SimpleReviewResponse> getMyReviews(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_USERNAME));
        List<Review> reviews = reviewRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "id"));

        return reviews.stream()
                .map(review -> SimpleReviewResponse.builder()
                        .title(review.getTitle())
                        .rating(review.getRating())
                        .image(review.getImage())
                        .isbn(review.getIsbn())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviews(String isbn) {
        List<Review> reviews = reviewRepository.findByIsbn(isbn);

        return reviews.stream()
                .map(review -> ReviewResponse.builder()
                        .id(review.getId())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .nickname(review.getUser().getNickname())
                        .profileImage(review.getUser().getProfileImage())
                        .build())
                .toList();
    }

    public void updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_REVIEW_ID));

        updateReviewFields(review, reviewUpdateRequest);
        reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_REVIEW_ID));

        reviewRepository.delete(review);
    }

    private void updateReviewFields(Review review, ReviewUpdateRequest request) {
        review.setContent(request.getContent());
        review.setRating(request.getRating());
    }

}