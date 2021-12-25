package com.blackdog.onetwo.configuration.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST("BAD_REQUEST", "잘못된 요청입니다."),
    USER_NOT_FOUND("USER_NOT_FOUND", "유저 정보가 없습니다."),
    KAKAO_USER_NOT_FOUND("KAKAO_USER_NOT_FOUND", "카카오 유저 정보가 없습니다."),
    LOGIN_EXPIRED("LOGIN_EXPIRED", "로그인이 만료되었습니다."),
    RESOURCE_FORBIDDEN("RESOURCE_FORBIDDEN", "해당 리소스에 접근하실 수 없습니다.");

    private String code;

    private String message;

}
