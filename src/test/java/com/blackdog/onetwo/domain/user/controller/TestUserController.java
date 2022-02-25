package com.blackdog.onetwo.domain.user.controller;

import com.blackdog.onetwo.common.TestAbstractController;
import com.blackdog.onetwo.domain.user.enums.*;
import com.blackdog.onetwo.domain.user.request.AddKakaoUserParam;
import com.blackdog.onetwo.domain.user.request.AddUserTastesParam;
import com.blackdog.onetwo.utils.JsonUtil;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserController extends TestAbstractController {

    private static final String BASE_URL = extractingRequestMapping(UserController.class);

    @Test
    void API_카카오로그인() throws Exception {
        AddKakaoUserParam param = AddKakaoUserParam.builder()
                .accessToken("8czXiuHVkdgjAgc4oRkkkH4R0g5YmtBo1ZE58wopcSEAAAF_MapJ4A")
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
                        requestHeaders(loginRequired()),
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
                        requestHeaders(loginRequired()),
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
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestHeaders(loginRequired()),
                        requestFields(
                                fieldWithPath("genderStep").type(JsonFieldType.STRING).description("성별")
                                    .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EC%84%B1%EB%B3%84_%ED%98%95%EC%8B%9D[코드 이동^]")),
                                fieldWithPath("priceStep").type(JsonFieldType.STRING).description("가격취향")
                                    .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EA%B0%80%EA%B2%A9_%EC%A4%91%EC%9A%94_%ED%98%95%EC%8B%9D[코드 이동^]")),
                                fieldWithPath("alcoholStep").type(JsonFieldType.STRING).description("음주 취향")
                                        .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EC%9D%8C%EC%A3%BC_%EC%84%A0%ED%98%B8_%ED%98%95%EC%8B%9D[코드 이동^]")),
                                fieldWithPath("freshFoodStep").type(JsonFieldType.STRING).description("채식주의? 취향")
                                        .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EC%B1%84%EC%8B%9D_%EC%84%A0%ED%98%B8_%ED%98%95%EC%8B%9D[코드 이동^]")),
                                fieldWithPath("playStep").type(JsonFieldType.STRING).description("놀이 취향")
                                        .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EB%86%80%EC%9D%B4_%EC%84%A0%ED%98%B8_%ED%98%95%EC%8B%9D[코드 이동^]")),
                                fieldWithPath("timeStep").type(JsonFieldType.STRING).description("시간대 취향")
                                        .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EC%8B%9C%EA%B0%84%EB%8C%80_%EC%84%A0%ED%98%B8_%ED%98%95%EC%8B%9D[코드 이동^]"))
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, true)
                        )
                ));

    }

}
