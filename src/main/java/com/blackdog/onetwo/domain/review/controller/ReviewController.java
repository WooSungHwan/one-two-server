package com.blackdog.onetwo.domain.review.controller;

import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.common.order.Order;
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
     *     리뷰 삭제
     * </pre>
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteReview(@PathVariable("id") long id, @AuthenticationPrincipal SecurityUser securityUser) throws Exception {
        reviewService.deleteReview(
                id,
                securityUser);
        return null;
    }

}
