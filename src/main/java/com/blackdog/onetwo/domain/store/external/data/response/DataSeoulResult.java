package com.blackdog.onetwo.domain.store.external.data.response;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class DataSeoulResult {

    private String managementId;
    private String storeName;
    private String businessItem;
    private String detailState;
    private String approvalDate;
    private String jibunAddress;
    private String roadAddress;

    public static DataSeoulResult of(DataSeoulInfo info) {
        return new DataSeoulResult(
                info.getManageId(),
                info.getStoreName(),
                info.getBusinessItem(),
                info.getDetailState(),
                info.getApprovalDate(),
                info.getJibunAddress(),
                info.getRoadAddress());
    }

    public static DataSeoulResult of(DataSeoul info) {
        return new DataSeoulResult(
                info.getManageId(),
                info.getStoreName(),
                info.getBusinessItem(),
                info.getDetailState(),
                info.getApprovalDate(),
                info.getJibunAddress(),
                info.getRoadAddress());
    }

}
