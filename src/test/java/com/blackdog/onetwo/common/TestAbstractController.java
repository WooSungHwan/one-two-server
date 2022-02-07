package com.blackdog.onetwo.common;

import com.blackdog.onetwo.CustomRestDocumentationExtension;
import com.blackdog.onetwo.OneTwoServerApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@ExtendWith({SpringExtension.class, CustomRestDocumentationExtension.class})
@SpringBootTest(classes = OneTwoServerApplication.class)
public abstract class TestAbstractController {

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    protected RestDocumentationResultHandler document;

    @BeforeEach
    public void setUp(WebApplicationContext webAppContext, RestDocumentationContextProvider restDocumentation) {
        this.document = document("{class-name}/{method-name}"
                , preprocessRequest(modifyUris().host("http").host("ec2-54-180-30-10.ap-northeast-2.compute.amazonaws.com").port(5510))
                , preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true), springSecurityFilterChain)
                .apply(documentationConfiguration(restDocumentation)).alwaysDo(document).build();
    }

    protected static final String extractingRequestMapping(Class<?> clazz) {
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
        return Optional.ofNullable(requestMapping)
                .map(rm -> ArrayUtils.isNotEmpty(rm.value()) ? rm.value()[0] : rm.path()[0])
                .orElseThrow(() -> new RuntimeException("BASE URL을 찾을 수 없습니다."));

    }

    protected static final FieldDescriptor[] getRestResponseDescriptor(JsonFieldType resultType, boolean isOptional, FieldDescriptor... fieldDescriptors) {
        List<FieldDescriptor> fieldDescriptorList = new ArrayList<>();

        FieldDescriptor resultDescriptor = fieldWithPath(JsonFieldType.ARRAY.equals(resultType) ? "result[]" : "result").type(resultType).description("결과");
        if (isOptional) {
            resultDescriptor.optional();
        }
        fieldDescriptorList.add(fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"));
        fieldDescriptorList.add(resultDescriptor);
        fieldDescriptorList.addAll(List.of(fieldDescriptors));
        fieldDescriptorList.add(fieldWithPath("responseTime").type(JsonFieldType.STRING).description("API 응답 시간").attributes(getAttribute("yyyy.MM.dd hh:mm:ss")));

        return fieldDescriptorList.toArray(new FieldDescriptor[]{});
    }

    protected static final Attribute getAttribute(String value) {
        return new Attribute("format", value);
    }

    protected String getToken() {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPTkUtVFdPLVNFUlZFUiBBUEkiLCJpc3MiOiJPTkUtVFdPLVVTRVIiLCJpYXQiOjE2NDQyMzc5MjMsImV4cCI6MTY0NzExNzkyMywic2VxIjoyNTV9.yB31gquDIXZltud5DMfo_RGvzs6DvGkQH2yoixMyvCs";
    }

    protected HeaderDescriptor loginRequired() {
        return headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰");
    }
}
