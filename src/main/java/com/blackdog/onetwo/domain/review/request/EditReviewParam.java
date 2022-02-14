package com.blackdog.onetwo.domain.review.request;

import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class EditReviewParam {

    @Length(min = 1, max = 40, message = "리뷰 제목을 최소 {min}자 이상, 최대 {max}자 이하로 입력해주세요.")
    @NotBlank(message = "리뷰 제목을 입력해주세요.")
    private String title;

    @Length(min = 30, message = "리뷰 내용을 {min}자 이상 입력해주세요.")
    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String content;

    private Long storeId;

    @NotEmpty(message = "리뷰 관련 사진을 등록해주세요.")
    private Set<String> images;

    @NotEmpty(message = "리뷰 관련 태그를 선택해주세요.")
    private Set<ReviewTag> tags;
}
