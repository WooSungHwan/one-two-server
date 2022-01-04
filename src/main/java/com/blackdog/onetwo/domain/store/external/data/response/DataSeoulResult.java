package com.blackdog.onetwo.domain.store.external.data.response;

import lombok.Value;

@Value(staticConstructor = "of")
public class DataSeoulResult {

    private String managementId;
    private String jibunAddress;
    private String roadAddress;

}
