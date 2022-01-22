package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FreshFoodStep implements BaseEnum {

    LIKE("LIKE", "선호하는 편이에요."),
    HATE("HATE", "선호하지 않아요.");

    private String type;
    private String name;

    public static FreshFoodStep find(String type) {
        return BaseEnum.find(type, values());
    }

    public static FreshFoodStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

}
