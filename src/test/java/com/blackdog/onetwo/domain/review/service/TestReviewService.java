package com.blackdog.onetwo.domain.review.service;

import com.blackdog.onetwo.common.TestAbstractService;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.mapstruct.ReviewMapstruct;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewResult;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import com.blackdog.onetwo.domain.store.service.StoreService;
import com.blackdog.onetwo.domain.user.result.UserResult;
import com.blackdog.onetwo.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.blackdog.onetwo.domain.review.ReviewFixture.standardReview;
import static com.blackdog.onetwo.domain.review.ReviewFixture.standardReviewResult;
import static com.blackdog.onetwo.domain.store.StoreFixture.standardStoreResult;
import static com.blackdog.onetwo.domain.user.UserFixture.standardUserResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TestReviewService extends TestAbstractService {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private ReviewMapstruct reviewMapstruct;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void getReviewTest() {
        //given
        Review review = standardReview();
        ReviewResult reviewResult = standardReviewResult();
        UserResult userResult = standardUserResult();
        StoreResult storeResult = standardStoreResult();
        ReviewDetailResult result = new ReviewDetailResult(reviewResult, storeResult, userResult);

        when(reviewRepository.findById(eq(review.getId()))).thenReturn(Optional.of(review));
        when(userService.getUserResult(eq(review.getUsers()))).thenReturn(userResult);
        when(storeService.getStoreResult(eq(review.getStore()))).thenReturn(storeResult);
        when(reviewMapstruct.reviewToReviewResult(eq(review))).thenReturn(reviewResult);
        when(reviewMapstruct.makeReviewDetailResult(reviewResult, userResult, storeResult)).thenReturn(result);

        // call
        ReviewDetailResult reviewDetailResult = reviewService.getReview(review.getId());

        assertThat(reviewDetailResult).isNotNull();
        assertThat(reviewDetailResult).extracting("review").extracting("id").isEqualTo(review.getId());

        // verify call to mock
        verify(reviewRepository, times(1)).findById(eq(review.getId()));
        verify(reviewMapstruct, times(1)).reviewToReviewResult(eq(review));
        verify(reviewMapstruct, times(1)).makeReviewDetailResult(eq(reviewResult), eq(userResult), eq(storeResult));
        verify(userService, times(1)).getUserResult(eq(review.getUsers()));
        verify(storeService, times(1)).getStoreResult(eq(review.getStore()));
    }

}
