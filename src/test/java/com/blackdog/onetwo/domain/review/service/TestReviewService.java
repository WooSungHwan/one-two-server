package com.blackdog.onetwo.domain.review.service;

import com.blackdog.onetwo.common.TestAbstractService;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.mapstruct.ReviewMapstruct;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewListResult;
import com.blackdog.onetwo.domain.review.result.ReviewResult;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import com.blackdog.onetwo.domain.store.service.StoreService;
import com.blackdog.onetwo.domain.user.result.UserResult;
import com.blackdog.onetwo.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.blackdog.onetwo.domain.review.ReviewFixture.*;
import static com.blackdog.onetwo.domain.store.StoreFixture.standardStoreResult;
import static com.blackdog.onetwo.domain.user.UserFixture.standardUserResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TestReviewService extends TestAbstractService {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapstruct reviewMapstruct;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void getReviewTest() {
        //given
        Review review = standardReview();
        ReviewResult reviewResult = standardReviewResult();
        // TODO 어떻게 할지 생각해보자. 일단 null로 넣어보기
        ReviewDetailResult result = new ReviewDetailResult(reviewResult, null, null);

        when(reviewRepository.findByIdFetch(eq(review.getId()))).thenReturn(Optional.of(review));
        when(reviewMapstruct.reviewToReviewDetailResult(review)).thenReturn(result);

        // call
        ReviewDetailResult reviewDetailResult = reviewService.getReview(review.getId());

        assertThat(reviewDetailResult).isNotNull();
        assertThat(reviewDetailResult).extracting("review").extracting("id").isEqualTo(review.getId());

        // verify call to mock
        verify(reviewRepository, times(1)).findByIdFetch(eq(review.getId()));
        verify(reviewMapstruct, times(1)).reviewToReviewDetailResult(eq(review));
    }



}
