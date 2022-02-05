package com.blackdog.onetwo.domain.user.controller;

import com.blackdog.onetwo.configuration.security.JwtProvider;
import com.blackdog.onetwo.configuration.security.SecurityUser;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.request.AddUserTastesParam;
import com.blackdog.onetwo.domain.user.result.LoginResult;
import com.blackdog.onetwo.domain.user.result.UserResult;
import com.blackdog.onetwo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    /**
     * <pre>
     *     내정보 보기
     * </pre>
     * @return
     * @throws Exception
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getUser(@AuthenticationPrincipal SecurityUser securityUser) throws Exception {
        return userService.getUser(securityUser);
    }

    /**
     * <pre>
     *     카카오 로그인 & 카카오 유저 등록
     * </pre>
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/kakao-login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object kakaoLogin(@RequestBody @Validated AddKakaoUserParam param) throws Exception {
        UserResult userResult = userService.kakaoLogin(param);
        return LoginResult.of(userResult, jwtProvider.generateJwtToken(userResult.getId()));
    }

    /**
     * <pre>
     *     유저 회원 탈퇴
     * </pre>
     * @param securityUser
     * @return
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteUser(@AuthenticationPrincipal SecurityUser securityUser) {
        userService.deleteUser(securityUser);
        return null;
    }

    /**
     * <pre>
     *     유저 취향 선택
     * </pre>
     * @param param
     * @param securityUser
     * @return
     */
    @PostMapping(value = "/tastes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object addUsersTastes(@RequestBody @Validated AddUserTastesParam param,
                                 @AuthenticationPrincipal SecurityUser securityUser) {
        userService.addUsersTastes(param, securityUser);
        return null;
    }

}
