package com.blackdog.onetwo.domain.user;

import com.blackdog.onetwo.common.TestAbstractController;
import com.blackdog.onetwo.domain.user.controller.UserController;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.utils.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserController extends TestAbstractController {

    private static final String BASE_URL = extractingRequestMapping(UserController.class);

    @Test
    void API_카카오로그인() throws Exception {
        AddKakaoUserParam param = AddKakaoUserParam.builder()
                .accessToken("JkSqIyk3CKnCpEYFLaIzynBiChoKmxCHZnKCnQo9dZsAAAF95gTpIQ")
                .build();

        mockMvc.perform(post(BASE_URL + "/kakao-login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.toJson(param)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("카카오 엑세스 토큰")
                        ),
                        responseFields(
                                getRestResponseDescriptor(
                                        JsonFieldType.OBJECT, false,
                                        fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("유저 번호"),
                                        fieldWithPath("result.nickname").type(JsonFieldType.STRING).description("닉네임")
                                )
                        )
                ));

    }

}
