package com.blackdog.onetwo.domain.review.controller;

import com.blackdog.onetwo.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object getReview(@PathVariable("id") Long id) throws Exception {
        return reviewService.getReview(id);
    }

}
