package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderStep implements BaseEnum {

    MALE("MALE", "남성"),
    FEMALE("FEMALE", "여성"),
    NONE("NONE", "선택 안할래요.");

    private String type;
    private String name;

    public static GenderStep find(String type) {
        return BaseEnum.find(type, values());
    }

    public static GenderStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

}
