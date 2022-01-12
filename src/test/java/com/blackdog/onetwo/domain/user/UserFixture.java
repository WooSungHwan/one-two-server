package com.blackdog.onetwo.domain.user;

import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.result.UserResult;

public class UserFixture {

    public static Users standardUsers() {
        return Users.builder()
                .id(1L)
                .nickname("woo")
                .kakaoId("193293892")
                .build();
    }

    public static UserResult standardUserResult() {
        Users entity = standardUsers();
        return UserResult.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .build();
    }

}
