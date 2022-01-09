package com.blackdog.onetwo.domain.review.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewResult;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import com.blackdog.onetwo.domain.user.result.UserResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfig.class, imports = {})
public interface ReviewMapstruct {

    @Mappings({})
    ReviewResult reviewToReviewResult(Review review);

    @Mappings({
        @Mapping(target = "review", expression = "java(review)"),
        @Mapping(target = "store", expression = "java(store)"),
        @Mapping(target = "user",  expression = "java(user)")
    })
    ReviewDetailResult makeReviewDetailResult(ReviewResult review,
                                              UserResult user,
                                              StoreResult store);

}
