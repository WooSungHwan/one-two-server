package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeStep implements BaseEnum {

    AM("AM", "오전이 편해요."),
    PM("PM", "오후가 편해요.");

    private String type;
    private String name;

    public static TimeStep find(String type) {
        return BaseEnum.find(type, values());
    }

    @JsonCreator
    public static TimeStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

}
