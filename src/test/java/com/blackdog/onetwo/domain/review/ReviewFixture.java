package com.blackdog.onetwo.domain.review;

import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.result.ReviewResult;

import java.time.LocalDateTime;

import static com.blackdog.onetwo.domain.store.StoreFixture.standardStore;
import static com.blackdog.onetwo.domain.user.UserFixture.standardUsers;

public class ReviewFixture {

    public static Review standardReview() {
        return Review.builder()
                .id(1L)
                .content("리뷰입니다.")
                .store(standardStore())
                .users(standardUsers())
                .build();
    }

    public static ReviewResult standardReviewResult() {
        Review review = standardReview();
        return ReviewResult.builder()
                .id(review.getId())
                .content(review.getContent())
                .build();
    }

}
