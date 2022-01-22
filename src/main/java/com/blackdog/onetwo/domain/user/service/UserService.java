package com.blackdog.onetwo.domain.user.service;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.kakao.KakaoClient;
import com.blackdog.onetwo.domain.user.kakao.response.AuthResult;
import com.blackdog.onetwo.domain.user.mapstruct.UserMapstruct;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.result.UserResult;
import com.blackdog.onetwo.utils.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapstruct userMapstruct;
    private final KakaoClient kakaoClient;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResult kakaoLogin(AddKakaoUserParam param) {
        AuthResult authInfo = kakaoClient.getAuthInfo(param.getAccessToken());
        VerifyUtil.nonNull(authInfo, ErrorCode.KAKAO_USER_NOT_FOUND);

        if (!userRepository.existsByKakaoId(authInfo.getId())) {
            Users user = userRepository.save(Users.of(passwordEncoder.encode(authInfo.getId()), authInfo.getNickname(), authInfo.getId(), authInfo.getProfileImageUrl()));
            return getUserResult(user);
        }
        Users user = userRepository.findByKakaoId(authInfo.getId());
        return getUserResult(user);
    }

    public UserResult getUser(Long seq) {
        Users users = getValidatedUsers(seq);
        return getUserResult(users);
    }

    @Transactional
    public void deleteUser(SecurityUser securityUser) {
        Users users = getValidatedUsers(securityUser.getSeq());

        users.signOut();
    }

    public UserResult getUserResult(Users users) {
        return userMapstruct.usersToUserResult(users);
    }

    private Users getValidatedUsers(Long seq) {
        return userRepository.findById(seq)
                .orElseThrow(() -> new VerifyException(ErrorCode.USER_NOT_FOUND));
    }

}
