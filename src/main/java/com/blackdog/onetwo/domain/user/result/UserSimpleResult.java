package com.blackdog.onetwo.domain.user.result;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserSimpleResult {
    private String nickname;

    private String profile;
}
