package com.blackdog.onetwo.domain.review.repository;

import com.blackdog.onetwo.domain.review.entity.QReview;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.store.entity.QStore;
import com.blackdog.onetwo.domain.user.entity.QUsers;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReviewRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QReview review = QReview.review;
    private final QUsers user = QUsers.users;
    private final QStore store = QStore.store;

    public Optional<Review> findByIdFetch(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(review)
                    .innerJoin(review.users, user).fetchJoin()
                    .leftJoin(review.store, store).fetchJoin()
                    .where(eqId(id),
                           eqDeleted(false))
                    .fetchOne());
    }

    private BooleanExpression eqId(Long id) {
        return review.id.eq(id);
    }

    private BooleanExpression eqDeleted(boolean deleted) {
        return review.deleted.eq(deleted);
    }
}
