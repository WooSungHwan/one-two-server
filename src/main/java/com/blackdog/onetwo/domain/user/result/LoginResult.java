package com.blackdog.onetwo.domain.user.result;

import lombok.Value;

@Value(staticConstructor = "of")
public class LoginResult {

    private UserResult user;

    private String token;

}
