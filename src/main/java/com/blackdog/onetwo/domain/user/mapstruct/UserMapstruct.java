package com.blackdog.onetwo.domain.user.mapstruct;

import com.blackdog.onetwo.configuration.mapstruct.MapstructConfig;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.result.UserResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Objects;

@Mapper(config = MapstructConfig.class, imports = {Objects.class})
public interface UserMapstruct {

    @Mappings({
            @Mapping(target = "isFindFriends", expression = "java(Objects.isNull(user.getIsFindFriends()) ? false : user.getIsFindFriends())")
    })
    UserResult usersToUserResult(Users user);
}
