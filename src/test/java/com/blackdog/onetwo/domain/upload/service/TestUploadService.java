package com.blackdog.onetwo.domain.upload.service;

import com.blackdog.onetwo.configuration.properties.AmazonS3Properties;
import com.blackdog.onetwo.domain.upload.request.UploadImageParam;
import com.blackdog.onetwo.domain.upload.result.UploadImageResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestUploadService {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private AmazonS3Properties s3Properties;

    @Test
    void imageUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, FileCopyUtils.copyToByteArray(new ClassPathResource("image/test.png").getInputStream()));
        UploadImageParam param = UploadImageParam.builder()
                .image(file)
                .build();

        UploadImageResult uploadImageResult = uploadService.imageUpload(param);
        assertThat(uploadImageResult).isNotNull();
        assertThat(uploadImageResult).extracting("id").isNotNull();
        assertThat(uploadImageResult).extracting("url").asString().contains(s3Properties.getUrlPrefix());
        System.out.println(uploadImageResult.getUrl());
    }

}
