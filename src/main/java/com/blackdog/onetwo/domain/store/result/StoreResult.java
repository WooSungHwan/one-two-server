package com.blackdog.onetwo.domain.store.result;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class StoreResult {
    private String managementId;
    private String jibunAddress;
    private String roadAddress;
    private String businessItem; // 업태
}
