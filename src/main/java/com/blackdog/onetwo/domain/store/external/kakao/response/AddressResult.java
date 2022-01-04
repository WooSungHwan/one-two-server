package com.blackdog.onetwo.domain.store.external.kakao.response;

import lombok.ToString;
import lombok.Value;

@Value(staticConstructor = "of")
public class AddressResult {
    private Double x;
    private Double y;
}
