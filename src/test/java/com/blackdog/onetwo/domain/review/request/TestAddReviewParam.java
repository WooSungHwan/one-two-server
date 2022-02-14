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
import java.util.Set;
import java.util.stream.Stream;

import static com.blackdog.onetwo.domain.review.enums.ReviewTag.CHEAP;
import static com.blackdog.onetwo.domain.review.enums.ReviewTag.NO_KIDS_ZONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.commons.function.Try.success;

public class TestAddReviewParam extends TestAbstractParam {

    @ParameterizedTest
    @ArgumentsSource(value = AddReviewParamSuccessProvider.class)
    void 리뷰등록_정상_파라미터_테스트(String title,
                               String content,
                               Long storeId,
                               Set<String> images,
                               Set<ReviewTag> tags) {
        // given
        AddReviewParam param = makeAddReviewParam(title, content, storeId, images, tags);

        // when
        Set<ConstraintViolation<AddReviewParam>> validate = validator.validate(param, Order.class);

        // then
        assertThat(validate).isEmpty();

        success("테스트 성공");
    }

    private AddReviewParam makeAddReviewParam(String title,
                                              String content,
                                              Long storeId,
                                              Set<String> images,
                                              Set<ReviewTag> tags) {
        return AddReviewParam.builder()
                .title(title)
                .content(content)
                .storeId(storeId)
                .images(images)
                .tags(tags)
                .build();
    }

    private static class AddReviewParamSuccessProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of("제목입니다.", "내용입니다.", 1L, Set.of("test.png"), Set.of(NO_KIDS_ZONE, CHEAP)),
                    Arguments.of("제목입니다.", "내용입니다.", null, Set.of("test.png"), Set.of(NO_KIDS_ZONE, CHEAP))
            );
        }
    }

}
