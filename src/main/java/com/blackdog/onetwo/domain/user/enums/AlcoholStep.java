package com.blackdog.onetwo.domain.user.enums;

import com.blackdog.onetwo.configuration.converter.BaseEnumAttributeConverter;
import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public static AlcoholStep findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends BaseEnumAttributeConverter<AlcoholStep> {
        public Converter() {
            super(AlcoholStep.class);
        }
    }
}
