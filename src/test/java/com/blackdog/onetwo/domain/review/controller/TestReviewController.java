package com.blackdog.onetwo.domain.review.controller;

import com.blackdog.onetwo.common.TestAbstractController;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestReviewController extends TestAbstractController {

    private static final String BASE_URL = extractingRequestMapping(ReviewController.class);

    @Test
    void API_리뷰한건조회() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+getToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                        requestHeaders(
                                loginRequired()
                        ),
                        pathParameters(
                                parameterWithName("id").description("리뷰 번호")
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, false,
                                        // 리뷰 정보
                                        fieldWithPath("result.review").type(JsonFieldType.OBJECT).description("리뷰 정보"),
                                        fieldWithPath("result.review.id").type(JsonFieldType.NUMBER).description("리뷰 번호"),
                                        fieldWithPath("result.review.content").type(JsonFieldType.STRING).description("리뷰 상세 내용"),
                                        // 가게 정보
                                        fieldWithPath("result.store").type(JsonFieldType.OBJECT).description("가게 정보"),
                                        fieldWithPath("result.store.managementId").type(JsonFieldType.STRING).description("가게 관리 번호"),
                                        fieldWithPath("result.store.jibunAddress").type(JsonFieldType.STRING).description("가게 지번 주소"),
                                        fieldWithPath("result.store.roadAddress").type(JsonFieldType.STRING).description("가게 도로명 주소"),
                                        fieldWithPath("result.store.businessItem").type(JsonFieldType.STRING).description("가게 업태"),
                                        // 유저 정보
                                        fieldWithPath("result.user").type(JsonFieldType.OBJECT).description("유저 정보"),
                                        fieldWithPath("result.user.id").type(JsonFieldType.NUMBER).description("유저 번호"),
                                        fieldWithPath("result.user.nickname").type(JsonFieldType.STRING).description("유저 닉네임")
                                )
                        )
                ));
    }

}
