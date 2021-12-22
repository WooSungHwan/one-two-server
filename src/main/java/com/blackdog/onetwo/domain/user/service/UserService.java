package com.blackdog.onetwo.domain.user.service;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.domain.user.entity.UserEntity;
import com.blackdog.onetwo.domain.user.kakao.KakaoClient;
import com.blackdog.onetwo.domain.user.kakao.response.AuthResult;
import com.blackdog.onetwo.domain.user.mapstruct.UserMapstruct;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.result.UserResult;
import com.blackdog.onetwo.utils.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapstruct userMapstruct;
    private final KakaoClient kakaoClient;

    @Transactional
    public UserResult kakaoLogin(AddKakaoUserParam param) {
        AuthResult authInfo = kakaoClient.getAuthInfo(param.getAccessToken());
        VerifyUtil.nonNull(authInfo, ErrorCode.KAKAO_USER_NOT_FOUND);

        if (userRepository.existsByKakaoId(authInfo.getId())) {
            UserEntity user = userRepository.findByKakaoId(authInfo.getId());
            return userMapstruct.userEntityToUserResult(user);
        }

        UserEntity user = userRepository.save(UserEntity.of(authInfo.getNickname(), authInfo.getId()));
        return userMapstruct.userEntityToUserResult(user);
    }
}
