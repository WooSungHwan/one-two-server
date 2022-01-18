package com.blackdog.onetwo.domain.upload.request;

import com.blackdog.onetwo.configuration.validation.Extension;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UploadImageParam {

    @NotNull(message = "이미지를 선택해주세요.")
    @Extension(allows = {"png", "jpg", "jpeg", "gif"}, message = "png, jpg, jpeg, gif 확장자 파일만 선택해주세요.")
    private MultipartFile image;

}
