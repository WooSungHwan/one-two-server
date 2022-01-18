package com.blackdog.onetwo.domain.upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.blackdog.onetwo.configuration.properties.AmazonProperties;
import com.blackdog.onetwo.configuration.properties.AmazonS3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AmazonS3Service {

    private final AmazonS3 s3;
    private final AmazonS3Properties properties;

    public void putObject(MultipartFile object, String key) throws Exception {
        ObjectMetadata metadata = getObjectMetadata(object);
        s3.putObject(properties.getBucket(), key, object.getInputStream(), metadata);
    }

    public String getUrlPrefix() {
        return properties.getUrlPrefix();
    }

    private ObjectMetadata getObjectMetadata(MultipartFile object) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(object.getContentType());
        metadata.setContentLength(object.getSize());
        return metadata;
    }
}
