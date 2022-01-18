package com.blackdog.onetwo.domain.upload.controller;

import com.blackdog.onetwo.domain.upload.request.UploadImageParam;
import com.blackdog.onetwo.domain.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/uploads")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object imageUpload(@ModelAttribute("uploadImageParam") @Validated UploadImageParam uploadImageParam) {
        return uploadService.imageUpload(uploadImageParam);
    }


}
