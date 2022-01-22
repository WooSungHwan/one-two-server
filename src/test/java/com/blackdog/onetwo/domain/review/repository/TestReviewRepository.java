package com.blackdog.onetwo.domain.review.repository;

import com.blackdog.onetwo.common.TestAbstractRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestReviewRepository extends TestAbstractRepository {

    @Autowired
    private ReviewRepository repository;

    @Test
    void maxTest() {
        Long id = repository.max();
        assertThat(id).isNotNull();
        assertThat(id).isPositive();
    }

    @Test
    void minTest() {
        Long id = repository.min();
        assertThat(id).isNotNull();
        assertThat(id).isPositive();

        assertThat(id).isEqualTo(1L);
    }

}
