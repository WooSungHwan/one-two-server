package com.blackdog.onetwo.domain.user.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserResult {
    private Long id;

    private String nickname;

    @JsonIgnore
    private String kakaoId;
}
