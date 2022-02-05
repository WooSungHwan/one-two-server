package com.blackdog.onetwo.domain.review;

import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.result.ReviewResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.blackdog.onetwo.domain.store.StoreFixture.standardStore;
import static com.blackdog.onetwo.domain.user.UserFixture.standardUsers;

public class ReviewFixture {

    public static Review standardReview() {
        return standardReview(1L);
    }

    public static ReviewResult standardReviewResult() {
        return standardReviewResult(standardReview());
    }

    public static List<Review> standardReviews() {
        return List.of(standardReview(1L), standardReview(2L), standardReview(3L));
    }

    public static List<ReviewResult> standardReviewResults() {
        return standardReviews().stream()
                .map(ReviewFixture::standardReviewResult)
                .collect(Collectors.toUnmodifiableList());
    }

    private static Review standardReview(long id) {
        return Review.builder()
                .id(1L)
                .content("리뷰입니다.")
                .store(standardStore())
                .users(standardUsers())
                .build();
    }

    private static ReviewResult standardReviewResult(Review review) {
        return ReviewResult.builder()
                .id(review.getId())
                .content(review.getContent())
                .build();
    }
}
