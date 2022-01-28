package com.blackdog.onetwo.domain.review.request;

import com.blackdog.onetwo.common.TestAbstractParam;
import com.blackdog.onetwo.domain.common.order.Order;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.blackdog.onetwo.domain.review.enums.ReviewTag.GOOD_PICTURE;
import static com.blackdog.onetwo.domain.review.enums.ReviewTag.NO_KIDS_ZONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.platform.commons.function.Try.success;

public class TestReviewListParam extends TestAbstractParam {

    @ParameterizedTest
    @ArgumentsSource(value = ReviewListParamSuccessProvider.class)
    void 리뷰목록_정상_파라미터_테스트(List<ReviewTag> tags,
                               Integer page,
                               Integer limit,
                               Long id) {
        // given
        ReviewListParam param = makeReviewListParam(tags, page, limit, id);

        // when
        Set<ConstraintViolation<ReviewListParam>> validate = validator.validate(param, Order.class);

        // then
        assertThat(validate).isEmpty();

        success("테스트 성공");
    }

    @ParameterizedTest
    @ArgumentsSource(value = ReviewListParamFailProvider.class)
    void 리뷰목록_실패_파라미터_테스트(List<ReviewTag> tags,
                              Integer page,
                              Integer limit,
                              Long id) {
        // given
        ReviewListParam param = makeReviewListParam(tags, page, limit, id);

        // when
        Set<ConstraintViolation<ReviewListParam>> validate = validator.validate(param, Order.class);

        // then
        assertThat(validate).hasSize(1);

        ConstraintViolation<ReviewListParam> valid = validate.stream().findFirst().get();
        System.out.println(String.format("%s : [%s : %s]", valid.getMessage(), valid.getPropertyPath(), valid.getInvalidValue()));

        success("테스트 성공");
    }

    private static class ReviewListParamSuccessProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 1, 5, null),
                    Arguments.of(Collections.emptyList(), 1, null, null),
                    Arguments.of(null, 2, null, 120L)
            );
        }
    }

    private static class ReviewListParamFailProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), null, null, null),
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), -1, null, null),
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 0, null, null),

                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 1, 11, null),
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 1, 4, null),

                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 1, 5, 120L),
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 2, 5, null),
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 2, 5, 0L),
                    Arguments.of(List.of(GOOD_PICTURE, NO_KIDS_ZONE), 2, 5, -1L)
            );
        }
    }

    private ReviewListParam makeReviewListParam(List<ReviewTag> tags,
                                                Integer page,
                                                Integer limit,
                                                Long id) {
        return ReviewListParam.builder()
                .tags(tags)
                .page(page)
                .limit(limit)
                .id(id)
                .build();
    }
}
