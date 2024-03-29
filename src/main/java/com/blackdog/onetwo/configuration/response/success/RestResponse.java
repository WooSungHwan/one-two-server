package com.blackdog.onetwo.configuration.response.success;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class RestResponse<T> {

    private String message;

    private T result;

    @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss")
    private LocalDateTime responseTime;

}
