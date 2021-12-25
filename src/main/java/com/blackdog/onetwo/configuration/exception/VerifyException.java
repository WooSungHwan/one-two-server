package com.blackdog.onetwo.configuration.exception;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;

public class VerifyException extends RuntimeException {

    private ErrorCode errorCode;

    public VerifyException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

}
