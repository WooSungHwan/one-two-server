package com.blackdog.onetwo.domain.user.kakao.response;

import lombok.Value;

@Value(staticConstructor = "of")
public class AuthResult {
    private String id;
    private String nickname;
}
