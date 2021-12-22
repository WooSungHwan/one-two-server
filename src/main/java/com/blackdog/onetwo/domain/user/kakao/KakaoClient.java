package com.blackdog.onetwo.domain.user.kakao;

import com.blackdog.onetwo.domain.user.kakao.response.AuthResult;
import com.blackdog.onetwo.utils.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoClient {

    private final RestTemplate restTemplate;
    private final String KAKAO_URL = "https://kapi.kakao.com/v2/user/me";

    public AuthResult getAuthInfo(String accessToken) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httpHeaders.set("Authorization", String.format("Bearer %s", accessToken));

            URI uri = URI.create(KAKAO_URL);

            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity(httpHeaders), String.class);

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("StatusCode = " + response.getStatusCode().value());
            }

            try {
                KakaoInfo kakaoInfo = JsonUtil.fromJson(response.getBody(), KakaoInfo.class);
                return AuthResult.of(kakaoInfo.getId(), kakaoInfo.getNickname());
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    private static class KakaoInfo {
        private long id;
        private LocalDateTime connectedAt;
        private Properties properties;
        private KakaoAccount kakaoAccount;

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @Data
        private static class Properties {
            private String nickname;
        }

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @Data
        private static class KakaoAccount {
            private boolean profileNeedsAgreement;
            private Profile profile;

            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
            @Data
            private static class Profile {
                private String nickname;
                private String thumbnailImageUrl;
                private String profileImageUrl;
                private boolean isDefaultImage;
            }
        }

        public String getId() {
            return String.valueOf(id);
        }

        public String getNickname() {
            return StringUtils.defaultIfBlank(kakaoAccount.getProfile().getNickname(), properties.getNickname());
        }
    }
}
