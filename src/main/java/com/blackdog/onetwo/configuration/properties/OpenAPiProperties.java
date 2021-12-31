package com.blackdog.onetwo.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "onetwo.open-api")
public class OpenAPiProperties {
    @NotBlank(message = "OPEN API 인증키를 설정해주세요.")
    private final String key;
}
