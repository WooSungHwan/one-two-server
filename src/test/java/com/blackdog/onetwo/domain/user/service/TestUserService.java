package com.blackdog.onetwo.domain.user.service;

import com.blackdog.onetwo.common.TestAbstractService;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.kakao.KakaoClient;
import com.blackdog.onetwo.domain.user.mapstruct.UserMapstruct;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import com.blackdog.onetwo.domain.user.result.UserResult;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.blackdog.onetwo.domain.user.UserFixture.standardUserResult;
import static com.blackdog.onetwo.domain.user.UserFixture.standardUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TestUserService extends TestAbstractService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapstruct userMapstruct;

    @Mock
    private KakaoClient kakaoClient;

    @InjectMocks
    private UserService userService;

    @Test
    void kakaoLoginTest() {

    }

    @Test
    void getUserTest() {

    }

    @Test
    void getUserResultTest() {
        // given
        UserResult userResult = standardUserResult();
        Users users = standardUsers();

        when(userMapstruct.usersToUserResult(eq(users))).thenReturn(userResult);

        // call
        UserResult result = userService.toUserResult(users);

        assertThat(result).isNotNull();
        assertThat(result).extracting("id").isEqualTo(users.getId());
        assertThat(result).extracting("nickname").isEqualTo(users.getNickname());

        //verify call
        verify(userMapstruct, times(1)).usersToUserResult(eq(users));
    }

}
