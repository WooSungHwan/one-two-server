package com.blackdog.onetwo.domain.user.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AddKakaoUserParam {

    @NotBlank(message = "카카오 정보를 입력해주세요.")
    private String accessToken;

}
