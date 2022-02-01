package com.blackdog.onetwo.domain.review.repository;

import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReviewRepositorySupport {

    Optional<Review> findByIdFetch(Long id);

    List<Review> findReviewsBySearch(List<ReviewTag> tags,
                                    Long id,
                                    int page,
                                    int limit);

}
