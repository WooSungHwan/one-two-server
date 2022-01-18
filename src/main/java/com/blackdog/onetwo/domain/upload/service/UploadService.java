package com.blackdog.onetwo.domain.upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.domain.upload.request.UploadImageParam;
import com.blackdog.onetwo.domain.upload.result.UploadImageResult;
import com.blackdog.onetwo.utils.VerifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.blackdog.onetwo.domain.upload.enums.FileDirectory.IMAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {

    private final AmazonS3Service amazonS3Service;

    public UploadImageResult imageUpload(UploadImageParam uploadImageParam) {
        MultipartFile image = uploadImageParam.getImage();
        String key = UUID.randomUUID().toString();
        String directoryUri = IMAGE.getDirectoryUrl(key);
        try {
            amazonS3Service.putObject(image, directoryUri);
        } catch (Exception e) {
            VerifyUtil.throwError(ErrorCode.FILE_UPLOADER_FAIL);
        }
        return UploadImageResult.of(key, String.format("%s/%s", amazonS3Service.getUrlPrefix(), directoryUri));
    }

}
