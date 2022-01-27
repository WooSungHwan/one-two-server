package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriceStep implements BaseEnum {

    IMPORTANT("IMPORTANT", "중요해요."),
    NOT_IMPORTANT("NOT_IMPORTANT", "중요하지 않아요.");

    private String type;
    private String name;

    public static PriceStep find(String type) {
        return BaseEnum.find(type, values());
    }

    @JsonCreator
    public static PriceStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

}
