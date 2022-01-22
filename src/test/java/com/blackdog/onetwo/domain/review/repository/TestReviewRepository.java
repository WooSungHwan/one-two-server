package com.blackdog.onetwo.domain.review.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestReviewRepository {

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
