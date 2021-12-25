package com.blackdog.onetwo.domain.user.controller;

import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * <pre>
     *     유저 조회
     * </pre>
     * @param seq
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{seq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getUser(@PathVariable("seq") Long seq) throws Exception {
        return userService.getUser(seq);
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
        return userService.kakaoLogin(param);
    }

}
