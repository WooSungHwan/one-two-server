package com.blackdog.onetwo.domain.common.result;

import lombok.Value;

@Value(staticConstructor = "of")
public class PatchResult {
    private Long id;
}
