package com.blackdog.onetwo.domain.upload.result;

import lombok.Value;

@Value(staticConstructor = "of")
public class UploadImageResult {
    private String id;

    private String url;
}
