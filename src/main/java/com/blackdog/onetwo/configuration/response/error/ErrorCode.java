package com.blackdog.onetwo.configuration.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST("BAD_REQUEST", "bad request");

    private String code;

    private String desc;

}
