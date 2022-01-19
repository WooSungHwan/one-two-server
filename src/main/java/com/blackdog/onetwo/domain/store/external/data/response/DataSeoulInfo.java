package com.blackdog.onetwo.domain.store.external.data.response;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class DataSeoulInfo {
    private String result;
    private List<DataSeoul> list;

    public String getManageId() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getManageId();
    }

    public String getJibunAddress() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getJibunAddress();
    }

    public String getRoadAddress() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getRoadAddress();
    }

    public String getStoreName() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getStoreName();
    }

    public String getBusinessItem() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getBusinessItem();
    }

    public String getApprovalDate() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getApprovalDate();
    }

    public String getDetailState() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getDetailState();
    }
}
