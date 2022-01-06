package com.blackdog.onetwo.domain.store.external.data.response;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value(staticConstructor = "of")
public class DataSeoulResult {

    private String managementId;
    private String jibunAddress;
    private String roadAddress;

    public static DataSeoulResult of(DataSeoulInfo info) {
        return new DataSeoulResult(info.getManageId(), info.getJibunAddress(), info.getRoadAddress());
    }

    public static DataSeoulResult of(DataSeoul info) {
        return new DataSeoulResult(info.getManageId(), info.getJibunAddress(), info.getRoadAddress());
    }

}
