package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlcoholStep implements BaseEnum {

    LIKE("LIKE", "좋아해요."),
    HATE("HATE", "싫어해요.");

    private String type;
    private String name;

    public static AlcoholStep find(String type) {
        return BaseEnum.find(type, values());
    }

    public static AlcoholStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

}
