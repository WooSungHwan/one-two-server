package com.blackdog.onetwo.domain.review.request;

import com.blackdog.onetwo.domain.common.pagination.MainListPageParam;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ReviewListParam extends MainListPageParam {

    private List<ReviewTag> tags;

}
