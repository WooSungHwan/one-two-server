package com.blackdog.onetwo.domain.user.service;

import com.blackdog.onetwo.configuration.exception.VerifyException;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.user.entity.UserTaste;
import com.blackdog.onetwo.domain.user.entity.Users;
import com.blackdog.onetwo.domain.user.kakao.KakaoClient;
import com.blackdog.onetwo.domain.user.kakao.response.AuthResult;
import com.blackdog.onetwo.domain.user.mapstruct.UserMapstruct;
import com.blackdog.onetwo.domain.user.repository.UserRepository;
import com.blackdog.onetwo.domain.user.repository.UserTasteRepository;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.request.AddUserTastesParam;
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
    private final UserTasteRepository userTasteRepository;
    private final UserMapstruct userMapstruct;
    private final KakaoClient kakaoClient;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResult kakaoLogin(AddKakaoUserParam param) {
        AuthResult authInfo = kakaoClient.getAuthInfo(param.getAccessToken());
        VerifyUtil.nonNull(authInfo, ErrorCode.KAKAO_USER_NOT_FOUND);

        if (!userRepository.existsByKakaoId(authInfo.getId())) {
            Users user = userRepository.save(Users.of(passwordEncoder.encode(authInfo.getId()), authInfo.getNickname(), authInfo.getId(), authInfo.getProfileImageUrl()));
            return toUserResult(user);
        }
        Users user = userRepository.findByKakaoId(authInfo.getId());
        return toUserResult(user);
    }

    public UserResult getUser(Long seq) {
        Users users = getValidatedUsers(seq);
        return toUserResult(users);
    }

    @Transactional
    public void deleteUser(SecurityUser securityUser) {
        Users users = getValidatedUsers(securityUser.getSeq());

        users.signOut();
    }

    public UserResult toUserResult(Users users) {
        return userMapstruct.usersToUserResult(users);
    }

    private Users getValidatedUsers(Long seq) {
        return userRepository.findById(seq)
                .orElseThrow(() -> new VerifyException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void addUsersTastes(AddUserTastesParam param,
                               SecurityUser securityUser) {
        // 기존 취향 있으면 삭제처리.
        Users users = getValidatedUsers(securityUser.getSeq());
        userTasteRepository.deleteUserTastesByUsers(users);

        UserTaste userTaste = UserTaste.of(
                users,
                param.getGenderStep(),
                param.getPriceStep(),
                param.getAlcoholStep(),
                param.getFreshFoodStep(),
                param.getPlayStep(),
                param.getTimeStep());

        userTasteRepository.save(userTaste);
    }
}
