package com.blackdog.onetwo.domain.review.controller;

import com.blackdog.onetwo.common.TestAbstractController;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.request.ReviewListParam;
import com.blackdog.onetwo.utils.JsonUtil;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.blackdog.onetwo.domain.review.enums.ReviewTag.CHEAP;
import static com.blackdog.onetwo.domain.review.enums.ReviewTag.NO_KIDS_ZONE;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestReviewController extends TestAbstractController {

    private static final String BASE_URL = extractingRequestMapping(ReviewController.class);

    @Autowired
    private ReviewRepository repository;

    private Long id;

    @BeforeEach
    public void setUp() {
        id = repository.max();
    }

    @Test
    void API_리뷰한건조회() throws Exception {
        mockMvc.perform(get(BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getToken()))
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
                                        fieldWithPath("result.store").type(JsonFieldType.OBJECT).description("가게 정보").optional(),
                                        fieldWithPath("result.store.managementId").type(JsonFieldType.STRING).description("가게 관리 번호").optional(),
                                        fieldWithPath("result.store.storeName").type(JsonFieldType.STRING).description("가게 이름").optional(),
                                        fieldWithPath("result.store.location").type(JsonFieldType.ARRAY).description("가게 위치 좌표").optional(),
                                        fieldWithPath("result.store.status").type(JsonFieldType.STRING).description("가게 운영 상태").optional(),
                                        fieldWithPath("result.store.jibunAddress").type(JsonFieldType.STRING).description("가게 지번 주소").optional(),
                                        fieldWithPath("result.store.roadAddress").type(JsonFieldType.STRING).description("가게 도로명 주소").optional(),
                                        fieldWithPath("result.store.businessItem").type(JsonFieldType.STRING).description("가게 업태").optional(),
                                        // 유저 정보
                                        fieldWithPath("result.user").type(JsonFieldType.OBJECT).description("유저 정보"),
                                        fieldWithPath("result.user.id").type(JsonFieldType.NUMBER).description("유저 번호"),
                                        fieldWithPath("result.user.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("result.user.isFindFriends").type(JsonFieldType.BOOLEAN).description("유저 동료찾기 활성화 여부"),
                                        fieldWithPath("result.user.profile").type(JsonFieldType.STRING).description("유저 프로필 사진")
                                )
                        )
                ));
    }

    @Test
    void API_리뷰목록() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("tags", NO_KIDS_ZONE.toString())
                .param("tags", CHEAP.toString())
                .param("page", "1")
                .param("limit", "5")
                .param("lastId", ""))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("tags").description("검색 태그").optional(),
                                parameterWithName("lastId").description("직전 마지막 행 아이디(page 값이 2 이상일때만)").optional(),
                                parameterWithName("page").description("검색 페이지"),
                                parameterWithName("limit").description("페이지당 목록 수(기본 5)").optional()
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, false,
                                        fieldWithPath("result.list[]").type(JsonFieldType.ARRAY).description("리뷰 목록"),

                                        fieldWithPath("result.list[].review").type(JsonFieldType.OBJECT).description("리뷰"),
                                        fieldWithPath("result.list[].review.id").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
                                        fieldWithPath("result.list[].review.content").type(JsonFieldType.STRING).description("리뷰 상세 내용"),
                                        fieldWithPath("result.list[].review.tags[]").type(JsonFieldType.ARRAY).description("리뷰 태그 목록"),
                                        fieldWithPath("result.list[].review.images[]").type(JsonFieldType.ARRAY).description("리뷰 이미지 목록"),

                                        fieldWithPath("result.list[].store").type(JsonFieldType.OBJECT).description("가게").optional(),
                                        fieldWithPath("result.list[].store.managementId").type(JsonFieldType.STRING).description("가게 관리 번호").optional(),
                                        fieldWithPath("result.list[].store.storeName").type(JsonFieldType.STRING).description("가게 이름").optional(),
                                        fieldWithPath("result.list[].store.location").type(JsonFieldType.ARRAY).description("가게 위치 좌표").optional(),
                                        fieldWithPath("result.list[].store.status").type(JsonFieldType.STRING).description("가게 운영 상태").optional(),
                                        fieldWithPath("result.list[].store.jibunAddress").type(JsonFieldType.STRING).description("가게 지번 주소").optional(),
                                        fieldWithPath("result.list[].store.roadAddress").type(JsonFieldType.STRING).description("가게 도로명 주소").optional(),
                                        fieldWithPath("result.list[].store.businessItem").type(JsonFieldType.STRING).description("가게 업태").optional(),

                                        fieldWithPath("result.list[].user").type(JsonFieldType.OBJECT).description("유저 내용"),
                                        fieldWithPath("result.list[].user.id").type(JsonFieldType.NUMBER).description("유저 번호"),
                                        fieldWithPath("result.list[].user.nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("result.list[].user.isFindFriends").type(JsonFieldType.BOOLEAN).description("유저 동료찾기 활성화 여부"),
                                        fieldWithPath("result.list[].user.profile").type(JsonFieldType.STRING).description("유저 프로필 사진"),

                                        fieldWithPath("result.lastId").type(JsonFieldType.NUMBER).description("최종 행 아이디")
                                )
                        )
                ))
        ;
    }

    @Test
    void API_리뷰삭제() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestHeaders(
                                loginRequired()
                        ),
                        pathParameters(
                                parameterWithName("id").description("리뷰 번호")
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.NULL, false)
                        )
                ));
    }

}
