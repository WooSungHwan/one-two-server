package com.blackdog.onetwo.domain.store.kakao.response;

import lombok.Value;

@Value(staticConstructor = "of")
public class AddressResult {
    private Double x;
    private Double y;
}
