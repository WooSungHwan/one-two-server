package com.blackdog.onetwo.domain.review.controller;

import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.common.order.Order;
import com.blackdog.onetwo.domain.review.request.AddReviewParam;
import com.blackdog.onetwo.domain.review.request.EditReviewParam;
import com.blackdog.onetwo.domain.review.request.ReviewListParam;
import com.blackdog.onetwo.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * <pre>
     *     리뷰 디테일 조회
     * </pre>
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getReview(@PathVariable("id") long id) throws Exception {
        return reviewService.getReview(id);
    }

    /**
     * <pre>
     *     리뷰 목록 조회(무한스크롤)
     *     기본 조회 Limit 5
     * </pre>
     * @param param
     * @return
     * @throws Exception
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getReviews(@ModelAttribute @Validated(Order.class) ReviewListParam param) throws Exception {
        return reviewService.getReviews(
                param.getTags(),
                param.getLastId(),
                param.getPage(),
                param.getLimit());
    }

    /**
     * <pre>
     *     리뷰 등록
     * </pre>
     * @param param
     * @param securityUser
     * @return
     * @throws Exception
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object addReview(@RequestBody @Validated AddReviewParam param,
                            @AuthenticationPrincipal SecurityUser securityUser) throws Exception {
        return reviewService.addReview(
                param.getTitle(),
                param.getContent(),
                param.getStoreId(),
                param.getImages(),
                param.getTags(),
                securityUser);
    }

    /**
     * <pre>
     *     리뷰 수정
     * </pre>
     * @param id
     * @param param
     * @param securityUser
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editReview(@PathVariable("id") long id,
                             @RequestBody @Validated EditReviewParam param,
                             @AuthenticationPrincipal SecurityUser securityUser) throws Exception {
        return reviewService.editReview(
                id,
                param.getTitle(),
                param.getContent(),
                param.getStoreId(),
                param.getImages(),
                param.getTags(),
                securityUser);
    }

    /**
     * <pre>
     *     리뷰 삭제
     * </pre>
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteReview(@PathVariable("id") long id,
                               @AuthenticationPrincipal SecurityUser securityUser) throws Exception {
        reviewService.deleteReview(
                id,
                securityUser);
        return null;
    }

}
