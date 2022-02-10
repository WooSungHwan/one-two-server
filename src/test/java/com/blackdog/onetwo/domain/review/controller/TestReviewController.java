package com.blackdog.onetwo.domain.review.controller;

import com.blackdog.onetwo.common.TestAbstractController;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.review.repository.ReviewRepository;
import com.blackdog.onetwo.domain.review.request.AddReviewParam;
import com.blackdog.onetwo.domain.review.request.ReviewListParam;
import com.blackdog.onetwo.utils.JsonUtil;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.Set;

import static com.blackdog.onetwo.domain.review.enums.ReviewTag.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
                        requestHeaders(loginRequired()),
                        pathParameters(
                                parameterWithName("id").description("리뷰 번호")
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, false,
                                        // 리뷰 정보
                                        fieldWithPath("result.review").type(JsonFieldType.OBJECT).description("리뷰 정보"),
                                        fieldWithPath("result.review.id").type(JsonFieldType.NUMBER).description("리뷰 번호"),
                                        fieldWithPath("result.review.content").type(JsonFieldType.STRING).description("리뷰 상세 내용"),
                                        fieldWithPath("result.review.title").type(JsonFieldType.STRING).description("리뷰 제목"),
                                        fieldWithPath("result.review.tags").type(JsonFieldType.ARRAY).description("리뷰 태그 목록"),
                                        fieldWithPath("result.review.images").type(JsonFieldType.ARRAY).description("리뷰 이미지 배열"),
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
    void API_리뷰등록() throws Exception {
        AddReviewParam param = AddReviewParam.builder()
                .title("리뷰 제목입니다.")
                .content("리뷰 내용입니다. 리뷰 내용은 30자를 넘겨야 합니다. 30자를 넘기기 매우 어렵네요.")
                .storeId(176L)
                .images(Set.of("test.png"))
                .tags(Set.of(NO_KIDS_ZONE, CHEAP, GOOD_PICTURE))
                .build();

        mockMvc.perform(post(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, getToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.toJson(param)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestHeaders(loginRequired()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("리뷰 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("가게 아이디"),
                                fieldWithPath("images").type(JsonFieldType.ARRAY).description("이미지 배열"),
                                fieldWithPath("tags").type(JsonFieldType.ARRAY).description("리뷰 태그 배열")
                                    .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EB%A6%AC%EB%B7%B0_%ED%83%9C%EA%B7%B8_%ED%98%95%EC%8B%9D[코드 이동^]"))
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, false,
                                        fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("생성된 리뷰 번호")
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
                                parameterWithName("tags").description("검색 태그").optional()
                                        .attributes(getAttribute("https://one-two-api-docs.s3.ap-northeast-2.amazonaws.com/one-two-api/code-adoc.html#%EB%A6%AC%EB%B7%B0_%ED%83%9C%EA%B7%B8_%ED%98%95%EC%8B%9D[코드 이동^]")),
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
                                        fieldWithPath("result.list[].review.title").type(JsonFieldType.STRING).description("리뷰 제목"),
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
                        requestHeaders(loginRequired()),
                        pathParameters(
                                parameterWithName("id").description("리뷰 번호")
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.NULL, false)
                        )
                ));
    }

}
