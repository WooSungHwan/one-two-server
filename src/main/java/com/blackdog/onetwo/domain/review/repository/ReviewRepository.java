package com.blackdog.onetwo.domain.review.repository;

import com.blackdog.onetwo.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositorySupport {

    @Query(value = "select max(id) from Review")
    Long max();

    @Query(value = "select min(id) from Review")
    Long min();
}
