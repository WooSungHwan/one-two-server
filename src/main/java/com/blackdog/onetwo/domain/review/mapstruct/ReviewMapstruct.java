package com.blackdog.onetwo.domain.review.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewResult;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import com.blackdog.onetwo.domain.user.result.UserResult;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MapstructConfig.class, imports = {})
public interface ReviewMapstruct {

    @Mappings({
            @Mapping(target = "tags", expression = "java(toTagStrs(review.getTags()))")
    })
    ReviewResult reviewToReviewResult(Review review);

    @Mappings({
        @Mapping(target = "review", expression = "java(review)"),
        @Mapping(target = "store", expression = "java(store)"),
        @Mapping(target = "user",  expression = "java(user)")
    })
    ReviewDetailResult makeReviewDetailResult(ReviewResult review,
                                              @Context UserResult user,
                                              @Context StoreResult store);

    default List<String> toTagStrs(List<ReviewTag> tags) {
        return tags.stream()
            .map(ReviewTag::getName)
            .collect(Collectors.toUnmodifiableList());
    }
}
