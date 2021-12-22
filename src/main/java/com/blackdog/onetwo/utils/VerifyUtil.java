package com.blackdog.onetwo.utils;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class VerifyUtil {

    public static final void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new VerifyException(errorCode);
        }
    }

    public static final void isFalse(boolean expression, ErrorCode errorCode) {
        if (expression) {
            throw new VerifyException(errorCode);
        }
    }

    public static final void nonNull(Object object, ErrorCode errorCode) {
        if (Objects.isNull(object)) {
            throw new VerifyException(errorCode);
        }
    }
}
