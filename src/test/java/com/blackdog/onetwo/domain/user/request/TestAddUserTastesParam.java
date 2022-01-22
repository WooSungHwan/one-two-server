package com.blackdog.onetwo.domain.user.request;


import com.blackdog.onetwo.common.TestAbstractParam;
import com.blackdog.onetwo.domain.user.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.commons.function.Try.success;

@Slf4j
public class TestAddUserTastesParam extends TestAbstractParam {

    @ParameterizedTest
    @ArgumentsSource(value = AddUserTastesParamSuccessProvider.class)
    void 취향선택_정상_파라미터_테스트(GenderStep genderStep,
                               PriceStep priceStep,
                               AlcoholStep alcoholStep,
                               FreshFoodStep freshFoodStep,
                               PlayStep playStep,
                               TimeStep timeStep) {
        // given
        AddUserTastesParam param = makeAddUserTastesParam(
                genderStep,
                priceStep,
                alcoholStep,
                freshFoodStep,
                playStep,
                timeStep);
        // when
        Set<ConstraintViolation<AddUserTastesParam>> validate = validator.validate(param);

        // then
        assertThat(validate).isEmpty();

        success("테스트 성공");
    }

    @ArgumentsSource(value = AddUserTastesParamFailProvider.class)
    @ParameterizedTest
    void 카카오로그인_BLANK_파라미터_테스트(GenderStep genderStep,
                                    PriceStep priceStep,
                                    AlcoholStep alcoholStep,
                                    FreshFoodStep freshFoodStep,
                                    PlayStep playStep,
                                    TimeStep timeStep) {
        // given
        AddUserTastesParam param = makeAddUserTastesParam(
                genderStep,
                priceStep,
                alcoholStep,
                freshFoodStep,
                playStep,
                timeStep);
        // when
        Set<ConstraintViolation<AddUserTastesParam>> validate = validator.validate(param);

        // then
        assertThat(validate).hasSize(1);

        if (Objects.isNull(genderStep)) {
            assertMessageEquals(param, validate, "genderStep", NotNull.class);
        } else if (Objects.isNull(priceStep)) {
            assertMessageEquals(param, validate, "priceStep", NotNull.class);
        } else if (Objects.isNull(alcoholStep)) {
            assertMessageEquals(param, validate, "alcoholStep", NotNull.class);
        } else if (Objects.isNull(freshFoodStep)) {
            assertMessageEquals(param, validate, "freshFoodStep", NotNull.class);
        } else if (Objects.isNull(playStep)) {
            assertMessageEquals(param, validate, "playStep", NotNull.class);
        } else if (Objects.isNull(timeStep)) {
            assertMessageEquals(param, validate, "timeStep", NotNull.class);
        }

        success("테스트 성공");
    }

    private AddUserTastesParam makeAddUserTastesParam(GenderStep genderStep, PriceStep priceStep, AlcoholStep alcoholStep, FreshFoodStep freshFoodStep, PlayStep playStep, TimeStep timeStep) {
        return AddUserTastesParam.builder()
                .genderStep(genderStep)
                .priceStep(priceStep)
                .alcoholStep(alcoholStep)
                .freshFoodStep(freshFoodStep)
                .playStep(playStep)
                .timeStep(timeStep)
                .build();
    }


    private static class AddUserTastesParamSuccessProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(GenderStep.MALE, PriceStep.IMPORTANT, AlcoholStep.HATE, FreshFoodStep.HATE, PlayStep.WITH_PLAY, TimeStep.PM)
            );
        }
    }

    private static class AddUserTastesParamFailProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(null, PriceStep.IMPORTANT, AlcoholStep.HATE, FreshFoodStep.HATE, PlayStep.WITH_PLAY, TimeStep.PM),
                    Arguments.of(GenderStep.MALE, null, AlcoholStep.HATE, FreshFoodStep.HATE, PlayStep.WITH_PLAY, TimeStep.PM),
                    Arguments.of(GenderStep.MALE, PriceStep.IMPORTANT, null, FreshFoodStep.HATE, PlayStep.WITH_PLAY, TimeStep.PM),
                    Arguments.of(GenderStep.MALE, PriceStep.IMPORTANT, AlcoholStep.HATE, null, PlayStep.WITH_PLAY, TimeStep.PM),
                    Arguments.of(GenderStep.MALE, PriceStep.IMPORTANT, AlcoholStep.HATE, FreshFoodStep.HATE, null, TimeStep.PM),
                    Arguments.of(GenderStep.MALE, PriceStep.IMPORTANT, AlcoholStep.HATE, FreshFoodStep.HATE, PlayStep.WITH_PLAY, null)
            );
        }
    }

}
