package com.blackdog.onetwo.domain.review.result;

import com.blackdog.onetwo.domain.store.result.StoreResult;
import com.blackdog.onetwo.domain.user.result.UserResult;
import lombok.Value;

@Value
public class ReviewDetailResult {

    private ReviewResult review;

    private StoreResult store;

    private UserResult user;

}
