package com.blackdog.onetwo.domain.review.service;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.mapstruct.ReviewMapstruct;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewListResult;
import com.blackdog.onetwo.domain.store.service.StoreService;
import com.blackdog.onetwo.domain.user.service.UserService;
import com.blackdog.onetwo.utils.VerifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final ReviewMapstruct reviewMapstruct;

    public ReviewDetailResult getReview(Long id) {
        Review review = reviewRepository.findByIdFetch(id)
                .orElseThrow(() -> new VerifyException(ErrorCode.RESOURCE_NOT_FOUND));

        return reviewMapstruct.makeReviewDetailResult(
                reviewMapstruct.reviewToReviewResult(review),
                userService.toUserResult(review.getUsers()),
                storeService.toStoreResult(review.getStore()));
    }

    public ReviewListResult getReviews(List<ReviewTag> tags,
                                       Long id,
                                       int page,
                                       int limit) {
        List<Review> reviews = reviewRepository.findReviewsBySearch(
                tags,
                id,
                page,
                limit);

        // TODO 추후 개선
        List<ReviewDetailResult> results = new ArrayList<>();
        reviews.forEach(review -> {
            ReviewDetailResult result = reviewMapstruct.makeReviewDetailResult(
                    reviewMapstruct.reviewToReviewResult(review),
                    userService.toUserResult(review.getUsers()),
                    storeService.toStoreResult(review.getStore()));
            results.add(result);
        });

        return ReviewListResult.of(results);
    }

    @Transactional
    public void deleteReview(Long id,
                             SecurityUser securityUser) {
        Review review = reviewRepository.findByIdFetch(id)
                .orElseThrow(() -> new VerifyException(ErrorCode.RESOURCE_NOT_FOUND));

        VerifyUtil.isTrue(review.isMine(securityUser.getSeq()), ErrorCode.RESOURCE_FORBIDDEN);

        review.delete();
    }
}
