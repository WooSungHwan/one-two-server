package com.blackdog.onetwo.domain.user.controller;

import com.blackdog.onetwo.common.TestAbstractController;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.utils.JsonUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserController extends TestAbstractController {

    private static final String BASE_URL = extractingRequestMapping(UserController.class);

    @Disabled
    @Test
    void API_카카오로그인() throws Exception {
        AddKakaoUserParam param = AddKakaoUserParam.builder()
                .accessToken("MovtOddavs8RLeATnPwpkJdPRbYl0reixApX6wopb9QAAAF-bRUzlw")
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
                                        fieldWithPath("result").type(JsonFieldType.OBJECT).description("결과 객체"),
                                        fieldWithPath("result.user").type(JsonFieldType.OBJECT).description("유저 정보 객체"),
                                        fieldWithPath("result.user.id").type(JsonFieldType.NUMBER).description("유저 번호"),
                                        fieldWithPath("result.user.nickname").type(JsonFieldType.STRING).description("카카오에서 가져온 닉네임"),
                                        fieldWithPath("result.token").type(JsonFieldType.STRING).description("JWT 토큰")
                                )
                        )
                ));

    }

    @Test
    void API_유저조회() throws Exception{
        final Long id = 1L;

        mockMvc.perform(get(BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        pathParameters(
                                parameterWithName("id").description("유저 번호")
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, false,
                                        fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("유저 번호"),
                                        fieldWithPath("result.nickname").type(JsonFieldType.STRING).description("카카오에서 가져온 닉네임")
                                )
                        )
                ))
        ;
    }

}
