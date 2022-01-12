package com.blackdog.onetwo.domain.user.result;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserResult {
    private Long id;

    private String nickname;
}
