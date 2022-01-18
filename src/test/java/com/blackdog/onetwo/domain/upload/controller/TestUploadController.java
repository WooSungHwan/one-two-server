package com.blackdog.onetwo.domain.upload.controller;

import com.blackdog.onetwo.common.TestAbstractController;
import com.blackdog.onetwo.domain.user.controller.UserController;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.FileCopyUtils;

import java.io.File;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUploadController extends TestAbstractController {

    private static final String BASE_URL = extractingRequestMapping(UploadController.class);

    @Test
    void API_이미지업로드() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "test.png", MediaType.IMAGE_PNG_VALUE, FileCopyUtils.copyToByteArray(new ClassPathResource("image/test.png").getInputStream()));

        mockMvc.perform(multipart(BASE_URL + "/images")
                .file(image)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestParts(
                                partWithName("image").description("업로드할 이미지")
                        ),
                        responseFields(
                                getRestResponseDescriptor(JsonFieldType.OBJECT, true,
                                        fieldWithPath("result").type(JsonFieldType.OBJECT).description("결과 객체"),
                                        fieldWithPath("result.id").type(JsonFieldType.STRING).description("파일 아이디"),
                                        fieldWithPath("result.url").type(JsonFieldType.STRING).description("파일 경로")
                                )
                        )
                ))
        ;

    }

}
