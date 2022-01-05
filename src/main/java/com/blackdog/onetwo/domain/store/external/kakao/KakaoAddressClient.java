package com.blackdog.onetwo.domain.store.external.kakao;

import com.blackdog.onetwo.domain.store.external.kakao.response.AddressResult;
import com.blackdog.onetwo.utils.JsonUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoAddressClient {

    private final RestTemplate restTemplate;
    private final String KAKAO_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public AddressResult addressToLocation(String address) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "KakaoAK cf6d2026face7a19e0a4d818ce63abca");
            httpHeaders.set("KA", "sdk/4.4.3 os/javascript lang/ko-KR device/MacIntel origin/https://www.deveapp.com");

            URI uri = new URIBuilder(KAKAO_ADDRESS_URL)
                    .addParameter("query", address)
                    .addParameter("page", "1")
                    .addParameter("size", "10")
                    .build();

            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity(httpHeaders), String.class);

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("StatusCode = " + response.getStatusCode().value());
            }

            try {
                AddressInfo addressInfo = JsonUtil.fromJson(response.getBody(), AddressInfo.class);
                return AddressResult.of(addressInfo.getFirstX(), addressInfo.getFirstY());
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Data
    private static class AddressInfo {
        private List<Document> documents;

        @Data
        private static class Document {
            private Double x;
            private Double y;
        }

        public Double getFirstX() {
            if (CollectionUtils.isEmpty(documents)) {
                return null;
            }
            return documents.get(0).getX();
        }

        public Double getFirstY() {
            if (CollectionUtils.isEmpty(documents)) {
                return null;
            }
            return documents.get(0).getY();
        }
    }
}
