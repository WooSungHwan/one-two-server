package com.blackdog.onetwo.configuration.mapstruct;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE, // 매핑안된 타겟 에 대한 정책 -> 무시
        uses = {}
)
public interface MapstructConfig {
}
