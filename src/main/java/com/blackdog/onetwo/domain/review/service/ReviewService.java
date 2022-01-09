package com.blackdog.onetwo.domain.review.service;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.mapstruct.ReviewMapstruct;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.store.service.StoreService;
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
    private final UserService userService;
    private final StoreService storeService;
    private final ReviewMapstruct reviewMapstruct;

    // TODO 단건이라 쿼리 최대 3개 나갈테지만 fetch join으로 수정 필요함. -> query dsl 도입 필요할듯. 목록개발에서 필요성은 더 대두됨.
    public ReviewDetailResult getReview(Long id) {
        Review review = reviewRepository.findById(id).get();

        VerifyUtil.nonNull(review, ErrorCode.RESOURCE_NOT_FOUND);

        return reviewMapstruct.makeReviewDetailResult(
                    reviewMapstruct.reviewToReviewResult(review),
                    userService.getUserResult(review.getUsers()),
                    storeService.getStoreResult(review.getStore()));
    }
}
