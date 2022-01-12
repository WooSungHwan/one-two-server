package com.blackdog.onetwo.domain.store;

import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.result.StoreResult;

public class StoreFixture {

    public static Store standardStore() {
        return Store.builder()
                .managementId("1")
                .roadAddress("의정부 가능로")
                .jibunAddress("의정부 의정부동")
                .approvalDate("20211010")
                .businessItem("양식")
                .build();
    }

    public static StoreResult standardStoreResult() {
        Store entity = standardStore();
        return StoreResult.builder()
                .managementId(entity.getManagementId())
                .businessItem(entity.getBusinessItem())
                .jibunAddress(entity.getJibunAddress())
                .roadAddress(entity.getRoadAddress())
                .build();
    }

}
