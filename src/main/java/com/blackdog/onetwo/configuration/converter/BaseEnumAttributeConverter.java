package com.blackdog.onetwo.configuration.converter;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@RequiredArgsConstructor
@Converter
public class BaseEnumAttributeConverter<T extends BaseEnum> implements AttributeConverter<T, String> {

    private final Class<T> clazz;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getType();
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        T[] enumConstants = clazz.getEnumConstants();
        for (T constant : enumConstants) {
            if (StringUtils.equals(constant.getType(), dbData)) {
                return constant;
            }
        }

        throw new UnsupportedOperationException(String.format("지원하지 않는 enum 형식입니다. : %s", dbData));
    }
}
