package com.blackdog.onetwo.domain.review.result;

import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class ReviewResult {
    private Long id;

    private String content;

    private List<String> tags;
}
