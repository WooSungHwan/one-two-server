package com.blackdog.onetwo.domain.user.request;

import com.blackdog.onetwo.common.TestAbstractParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.commons.function.Try.success;

@Slf4j
public class TestAddKakaoUserParam<T> extends TestAbstractParam {

    @ParameterizedTest
    @MethodSource("generateKakaoAccessToken")
    void 카카오로그인_정상_파라미터_테스트(String accessToken) {
        // given
        AddKakaoUserParam addKakaoUserParam = makeAddKakaoUserParam(accessToken);
        // when
        Set<ConstraintViolation<AddKakaoUserParam>> validate = validator.validate(addKakaoUserParam);
        // then
        assertThat(validate).isEmpty();

        success("테스트 성공");
    }

    static Stream<String> generateKakaoAccessToken() {
        return Stream.of("qAvmLyNzOdjIefIR8AR5ouBF-SOjJtM-TOIN0gorDNQAAAF-CO5nWg");
    }

    @ParameterizedTest
    @MethodSource("generateBlankKakaoAccessToken")
    void 카카오로그인_BLANK_파라미터_테스트(String accessToken) {
        // given
        AddKakaoUserParam addKakaoUserParam = makeAddKakaoUserParam(accessToken);
        // when
        Set<ConstraintViolation<AddKakaoUserParam>> validate = validator.validate(addKakaoUserParam);
        // then
        assertThat(validate).hasSize(1);
        assertMessageEquals(addKakaoUserParam, validate, "accessToken", NotBlank.class);

        success("테스트 성공");
    }

    static Stream<String> generateBlankKakaoAccessToken() {
        return Stream.of("", " ", null);
    }

    protected AddKakaoUserParam makeAddKakaoUserParam(String accessToken) {
        AddKakaoUserParam request = AddKakaoUserParam.builder()
                .accessToken(accessToken)
                .build();
        return request;
    }
}
