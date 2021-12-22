package com.blackdog.onetwo.domain.user.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.domain.user.entity.UserEntity;
import com.blackdog.onetwo.domain.user.result.UserResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfig.class, imports = {})
public interface UserMapstruct {

    @Mappings({})
    UserResult userEntityToUserResult(UserEntity user);
}
