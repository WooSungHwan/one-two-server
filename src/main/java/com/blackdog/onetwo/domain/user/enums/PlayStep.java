package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.configuration.converter.BaseEnumAttributeConverter;
import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public static PlayStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends BaseEnumAttributeConverter<PlayStep> {
        public Converter() {
            super(PlayStep.class);
        }
    }
}
