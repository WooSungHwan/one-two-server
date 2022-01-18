package com.blackdog.onetwo.domain.user.controller;

import com.blackdog.onetwo.configuration.security.JwtProvider;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.result.LoginResult;
import com.blackdog.onetwo.domain.user.result.UserResult;
import com.blackdog.onetwo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     *     유저 조회
     * </pre>
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getUser(@PathVariable("id") Long id) throws Exception {
        return userService.getUser(id);
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

}
