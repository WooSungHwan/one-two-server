package com.blackdog.onetwo.domain.review.repository;

import com.blackdog.onetwo.domain.review.entity.QReview;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.store.entity.QStore;
import com.blackdog.onetwo.domain.user.entity.QUsers;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReviewRepositorySupportImpl implements ReviewRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QReview review = QReview.review;
    private final QUsers user = QUsers.users;
    private final QStore store = QStore.store;

    @Override
    public Optional<Review> findByIdFetch(Long id) {
        BooleanExpression[] whereExpressions = BooleanExpressionBuilder.builder()
                .addCondition(eqId(id))
                .addCondition(eqDeleted(false))
                .build();

        return Optional.ofNullable(jpaQueryFactory.selectFrom(review)
                    .innerJoin(review.tags).fetchJoin()
                    .innerJoin(review.images).fetchJoin()
                    .innerJoin(review.users, user).fetchJoin()
                    .leftJoin(review.store, store).fetchJoin()
                    .where(whereExpressions)
                    .fetchOne());
    }

    private List<Review> findByIdsFetch(List<Long> ids) {
        BooleanExpression[] whereExpressions = BooleanExpressionBuilder.builder()
                .addCondition(eqIds(ids))
                .addCondition(eqDeleted(false))
                .build();

        return jpaQueryFactory.selectFrom(review)
                .innerJoin(review.tags).fetchJoin()
                .innerJoin(review.images).fetchJoin()
                .innerJoin(review.users, user).fetchJoin()
                .leftJoin(review.store, store).fetchJoin()
                .where(whereExpressions)
                .orderBy(review.id.desc())
                .distinct()
                .fetch();
    }

    @Override
    public List<Review> findReviewsBySearch(List<ReviewTag> tags,
                                            Long id,
                                            int page,
                                            int limit) {
        BooleanExpression[] whereExpressions = BooleanExpressionBuilder.builder()
                .addCondition(ltId(id, page))
                .addCondition(eqTag(tags))
                .build();
        
        List<Long> ids = jpaQueryFactory.select(review.id)
                .from(review)
                .where(whereExpressions)
                .orderBy(review.id.desc())
                .limit(limit)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        return findByIdsFetch(ids);
    }

    private BooleanExpression eqIds(List<Long> ids) {
        return review.id.in(ids);
    }

    private BooleanExpression eqId(Long id) {
        return review.id.eq(id);
    }

    private BooleanExpression eqDeleted(boolean deleted) {
        return review.deleted.eq(deleted);
    }

    private BooleanExpression ltId(Long id,
                                   Integer page) {
        if (page == 1) {
            return null;
        }
        return review.id.lt(id);
    }

    private BooleanExpression eqTag(List<ReviewTag> tags) {
        return review.tags.any().in(tags);
    }

    // TODO 밖으로 빼기
    private static class BooleanExpressionBuilder {
        private List<BooleanExpression> booleanExpressionList;

        private BooleanExpressionBuilder() {
            this.booleanExpressionList = new ArrayList<>();
        }

        public static BooleanExpressionBuilder builder() {
            return new BooleanExpressionBuilder();
        }

        public BooleanExpressionBuilder addCondition(BooleanExpression booleanExpression) {
            booleanExpressionList.add(booleanExpression);
            return this;
        }

        public BooleanExpressionBuilder addCondition(List<BooleanExpression> booleanExpression) {
            booleanExpressionList.addAll(booleanExpression);
            return this;
        }

        public BooleanExpression[] build() {
            return booleanExpressionList.toArray(new BooleanExpression[]{});
        }

    }
}
