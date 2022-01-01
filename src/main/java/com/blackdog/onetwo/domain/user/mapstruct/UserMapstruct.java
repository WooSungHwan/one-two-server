package com.blackdog.onetwo.domain.user.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.result.UserResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfig.class, imports = {})
public interface UserMapstruct {

    @Mappings({})
    UserResult usersToUserResult(Users user);
}
