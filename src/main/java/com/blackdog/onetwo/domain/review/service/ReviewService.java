package com.blackdog.onetwo.domain.review.service;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.mapstruct.ReviewMapstruct;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.repository.ReviewRepositorySupport;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewResult;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import com.blackdog.onetwo.domain.store.service.StoreService;
import com.blackdog.onetwo.domain.user.result.UserResult;
import com.blackdog.onetwo.domain.user.service.UserService;
import com.blackdog.onetwo.utils.VerifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewRepositorySupport reviewRepositorySupport;
    private final UserService userService;
    private final StoreService storeService;
    private final ReviewMapstruct reviewMapstruct;

    public ReviewDetailResult getReview(Long id) {
        Review review = reviewRepositorySupport.findByIdFetch(id)
                .orElseThrow(() -> new VerifyException(ErrorCode.RESOURCE_NOT_FOUND));

        return reviewMapstruct.makeReviewDetailResult(
                reviewMapstruct.reviewToReviewResult(review),
                userService.getUserResult(review.getUsers()),
                storeService.getStoreResult(review.getStore()));
    }

    @Transactional
    public void deleteReview(Long id,
                             SecurityUser securityUser) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new VerifyException(ErrorCode.RESOURCE_NOT_FOUND));

        VerifyUtil.isTrue(review.isMine(securityUser.getSeq()), ErrorCode.RESOURCE_FORBIDDEN);

        review.delete();
    }
}
