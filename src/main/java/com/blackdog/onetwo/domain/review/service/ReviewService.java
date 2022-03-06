package com.blackdog.onetwo.domain.review.service;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.common.result.PatchResult;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.mapstruct.ReviewMapstruct;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.common.result.AddResult;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewListResult;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.repository.StoreRepository;
import com.blackdog.onetwo.utils.VerifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        if (CollectionUtils.isEmpty(reviews)) {
            return ReviewListResult.empty();
        }

        List<Review> results = reviews.stream()
                .limit(limit)
                .collect(Collectors.toUnmodifiableList());

        boolean hasNext = reviews.size() != results.size();

        return ReviewListResult.of(reviewMapstruct.reviewsToReviewDetailResults(results), hasNext);
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
    public AddResult addReview(String title,
                               String content,
                               Long storeId,
                               Set<String> images,
                               Set<ReviewTag> tags,
                               SecurityUser securityUser) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new VerifyException(ErrorCode.STORE_NOT_FOUND));

        Review entity = reviewRepository.save(Review.of(title, content, store, securityUser.getUsers(), tags, images));
        return AddResult.of(entity.getId());
    }

    @Transactional
    public PatchResult editReview(long id,
                                  String title,
                                  String content,
                                  Long storeId,
                                  Set<String> images,
                                  Set<ReviewTag> tags,
                                  SecurityUser securityUser) {
        Review review = reviewRepository.findByIdFetch(id)
                .orElseThrow(() -> new VerifyException(ErrorCode.RESOURCE_NOT_FOUND));

        VerifyUtil.isTrue(review.isMine(securityUser.getSeq()), ErrorCode.RESOURCE_FORBIDDEN);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new VerifyException(ErrorCode.STORE_NOT_FOUND));

        review.editReview(title, content, store, images, tags);

        return PatchResult.of(review.getId());
    }
}
