package com.blackdog.onetwo.configuration.response.error;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class ErrorResponse {
    // TODO Serializer
    private ErrorCode errorCode;

    private LocalDateTime responseTime;
}
