package com.blackdog.onetwo.domain.review.repository;

import com.blackdog.onetwo.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
