package com.blackdog.onetwo.configuration.response.success;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class RestResponse<T> {

    private String message;

    private T result;

    private LocalDateTime responseTime;

}
