package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayStep implements BaseEnum {

    ONLY_FOOD("ONLY_FOOD", "밥만 먹고 싶어요."),
    WITH_PLAY("WITH_PLAY", "놀고도 싶어요.");

    private String type;
    private String name;

    public static PlayStep find(String type) {
        return BaseEnum.find(type, values());
    }

    public static PlayStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

}
