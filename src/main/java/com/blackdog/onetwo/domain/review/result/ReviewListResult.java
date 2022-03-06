package com.blackdog.onetwo.domain.review.result;

import lombok.Value;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Value
public class ReviewListResult {
    private List<ReviewDetailResult> list;

    private Long lastId;

    private boolean hasNext;

    public static ReviewListResult empty() {
        return new ReviewListResult(Collections.emptyList(), null, false);
    }

    public static ReviewListResult of(List<ReviewDetailResult> list, boolean hasNext) {
        if (CollectionUtils.isEmpty(list)) {
            return new ReviewListResult(Collections.emptyList(), null, false);
        }

        Long minId = list.stream()
                .map(result -> result.getReview().getId())
                .min(Comparator.comparing(result -> result))
                .orElseThrow(NoSuchElementException::new);

        return new ReviewListResult(list, minId, hasNext);
    }
}
