package com.blackdog.onetwo.domain.user.controller;

import com.blackdog.onetwo.common.TestAbstractController;
import com.blackdog.onetwo.domain.user.enums.*;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.request.AddUserTastesParam;
import com.blackdog.onetwo.utils.JsonUtil;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Optional;

import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserController extends TestAbstractController {

    private static final String BASE_URL = extractingRequestMapping(UserController.class);

    @Test
    void API_카카오로그인() throws Exception {
        AddKakaoUserParam param = AddKakaoUserParam.builder()
                .accessToken("VmB1gGvu2KP1uFoKafUXmwA-ILnv5rDiLFHwaAorDNQAAAF-f__6Nw")
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
                                        fieldWithPath("result.user.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("result.user.isFindFriends").type(JsonFieldType.BOOLEAN).description("동료찾기 여부").optional(),
                                        fieldWithPath("result.user.profile").type(JsonFieldType.STRING).description("프로필 사진"),
                                        fieldWithPath("result.token").type(JsonFieldType.STRING).description("JWT 토큰")
                                )
                        )
                ));

    }

    @Test
    void API_내정보() throws Exception{
        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestHeaders(
                                loginRequired()
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, false,
                                        fieldWithPath("result").type(JsonFieldType.OBJECT).description("결과 객체"),
                                        fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("유저 번호"),
                                        fieldWithPath("result.nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("result.profile").type(JsonFieldType.STRING).description("프로필 사진"),
                                        fieldWithPath("result.isFindFriends").type(JsonFieldType.BOOLEAN).description("동료찾기 여부")
                                )
                        )
                ));
    }

    @Test
    void API_회원탈퇴() throws Exception {
        mockMvc.perform(delete(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestHeaders(
                                loginRequired()
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.NULL, false)
                        )
                ))
        ;
    }

    @Test
    void API_취향선택() throws Exception {
        AddUserTastesParam param = AddUserTastesParam.builder()
                .genderStep(GenderStep.FEMALE)
                .priceStep(PriceStep.IMPORTANT)
                .alcoholStep(AlcoholStep.HATE)
                .freshFoodStep(FreshFoodStep.HATE)
                .playStep(PlayStep.WITH_PLAY)
                .timeStep(TimeStep.AM)
                .build();

        mockMvc.perform(post(BASE_URL + "/tastes")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getToken())
                .content(JsonUtil.toJson(param)))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
