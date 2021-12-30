package com.blackdog.onetwo.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class TestAbstractParam {
    protected Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    protected <T> void assertMessageEquals(T paramType,
                                           Set<ConstraintViolation<T>> validate,
                                           String fieldName,
                                           Class<? extends Annotation> annotationType) {
        try {
            final String message = getErrorMessage(paramType, fieldName, annotationType);
            validate.forEach(e -> {
                assertThat(e.getConstraintDescriptor().getMessageTemplate()).isEqualTo(message);
            });
        } catch (NoSuchFieldException e) {
            fail("테스트 실패 : 필드 정보를 확인해주세요.");
        }
    }

    private String getErrorMessage(Object o,
                                   String fieldName,
                                   Class<? extends Annotation> annotationType) throws NoSuchFieldException {
        Method method = ReflectionUtils.findMethod(annotationType, "message");

        return (String) (ReflectionUtils.invokeMethod(method, o.getClass().getDeclaredField(fieldName).getDeclaredAnnotation(annotationType)));
    }

}
