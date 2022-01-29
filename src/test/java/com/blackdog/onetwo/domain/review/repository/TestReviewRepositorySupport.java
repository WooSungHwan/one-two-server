package com.blackdog.onetwo.domain.review.repository;

import com.blackdog.onetwo.common.TestAbstractRepositorySupport;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestReviewRepositorySupport extends TestAbstractRepositorySupport {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void findByIdFetchTest() {
        long target = 180L;
        Optional<Review> review = reviewRepository.findByIdFetch(target);
        review.ifPresent(r -> {
            assertThat(r.getId()).isEqualTo(target);
        });
    }

    @Test
    void findReviewsBySearchTest() {
        List<ReviewTag> tags = List.of(ReviewTag.CHEAP, ReviewTag.NO_KIDS_ZONE);
        long pagingId = 180L;
        List<Review> reviews = reviewRepository.findReviewsBySearch(tags, pagingId, 2, 5);

        assertThat(reviews).hasSizeGreaterThanOrEqualTo(1);
        assertThat(reviews).hasSizeLessThanOrEqualTo(5);

        reviews.forEach(review -> {
            log.info("id : {}", review.getId());
            assertThat(review.getId()).isLessThan(pagingId);
            int retainBeforeSize = review.getTags().size();
            review.getTags().retainAll(tags);
            int retainAfterSize = review.getTags().size();
            assertTrue(retainAfterSize < retainBeforeSize);
        });
    }

}
