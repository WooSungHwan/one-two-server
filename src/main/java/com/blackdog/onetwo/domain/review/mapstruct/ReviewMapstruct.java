package com.blackdog.onetwo.domain.review.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.configuration.properties.AmazonS3Properties;
import com.blackdog.onetwo.domain.review.entity.Review;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.result.ReviewDetailResult;
import com.blackdog.onetwo.domain.review.result.ReviewResult;
import com.blackdog.onetwo.domain.store.mapstruct.StoreMapstruct;
import com.blackdog.onetwo.domain.store.result.StoreResult;
import com.blackdog.onetwo.domain.upload.enums.FileDirectory;
import com.blackdog.onetwo.domain.user.mapstruct.UserMapstruct;
import com.blackdog.onetwo.domain.user.result.UserResult;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapstructConfig.class, imports = {})
public abstract class ReviewMapstruct {

    @Autowired
    protected UserMapstruct userMapstruct;

    @Autowired
    protected StoreMapstruct storeMapstruct;

    @Autowired
    protected AmazonS3Properties amazonS3Properties;

    @Mappings({
            @Mapping(target = "tags", expression = "java(toTagStrs(review.getTags()))"),
            @Mapping(target = "images", expression = "java(toImageUrl(review.getImages()))")
    })
    public abstract ReviewResult reviewToReviewResult(Review review);

    @Mappings({
            @Mapping(target = "review", expression = "java(reviewToReviewResult(review))"),
            @Mapping(target = "store", expression = "java(storeMapstruct.storeToStoreResult(review.getStore()))"),
            @Mapping(target = "user",  expression = "java(userMapstruct.usersToUserResult(review.getUsers()))")
    })
    public abstract ReviewDetailResult reviewToReviewDetailResult(Review review);

    @IterableMapping(elementTargetType = ReviewDetailResult.class)
    public abstract List<ReviewDetailResult> reviewsToReviewDetailResults(List<Review> reviews);

    protected List<String> toTagStrs(Set<ReviewTag> tags) {
        return tags.stream()
            .map(ReviewTag::getName)
            .collect(Collectors.toUnmodifiableList());
    }

    protected List<String> toImageUrl(Set<String> images) {
        return images.stream()
                .map(FileDirectory.IMAGE::getDirectoryUrl)
                .map(directiryUrl -> String.format("%s/%s", amazonS3Properties.getUrlPrefix(), directiryUrl))
                .collect(Collectors.toUnmodifiableList());
    }
}
