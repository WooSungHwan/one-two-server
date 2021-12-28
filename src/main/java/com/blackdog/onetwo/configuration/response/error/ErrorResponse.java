package com.blackdog.onetwo.configuration.response.error;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class ErrorResponse {
    private ErrorCode errorCode;

    private LocalDateTime responseTime;

    public static ErrorResponse of (ErrorCode errorCode) {
        return ErrorResponse.of(errorCode, LocalDateTime.now());
    }
}
