package com.blackdog.onetwo.domain.review.result;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ReviewResult {
    private Long id;

    private String content;
}
