package com.blackdog.onetwo.domain.review.service;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.mapstruct.ReviewMapstruct;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.result.AddReviewResult;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewListResult;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.repository.StoreRepository;
import com.blackdog.onetwo.domain.store.service.StoreService;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import com.blackdog.onetwo.domain.user.service.UserService;
import com.blackdog.onetwo.utils.VerifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final ReviewMapstruct reviewMapstruct;

    public ReviewDetailResult getReview(Long id) {
        Review review = reviewRepository.findByIdFetch(id)
                .orElseThrow(() -> new VerifyException(ErrorCode.RESOURCE_NOT_FOUND));

        return reviewMapstruct.reviewToReviewDetailResult(review);
    }

    public ReviewListResult getReviews(List<ReviewTag> tags,
                                       Long id,
                                       int page,
                                       int limit) {
        List<Review> reviews = reviewRepository.findReviewsBySearch(
                tags,
                id,
                page,
                limit + 1);

        return ReviewListResult.of(reviewMapstruct.reviewsToReviewDetailResults(reviews));
    }

    @Transactional
    public void deleteReview(Long id,
                             SecurityUser securityUser) {
        Review review = reviewRepository.findByIdFetch(id)
                .orElseThrow(() -> new VerifyException(ErrorCode.RESOURCE_NOT_FOUND));

        VerifyUtil.isTrue(review.isMine(securityUser.getSeq()), ErrorCode.RESOURCE_FORBIDDEN);

        review.delete();
    }

    @Transactional
    public AddReviewResult addReview(String title,
                                     String content,
                                     Long storeId,
                                     Set<String> images,
                                     Set<ReviewTag> tags,
                                     SecurityUser securityUser) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new VerifyException(ErrorCode.STORE_NOT_FOUND));

        Review entity = reviewRepository.save(Review.of(title, content, store, securityUser.getUsers(), tags, images));
        return AddReviewResult.of(entity.getId());
    }
}
