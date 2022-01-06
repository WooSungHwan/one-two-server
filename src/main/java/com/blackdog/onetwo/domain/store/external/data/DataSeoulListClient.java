package com.blackdog.onetwo.domain.store.external.data;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.domain.store.external.data.response.DataSeoul;
import com.blackdog.onetwo.domain.store.external.data.response.DataSeoulInfo;
import com.blackdog.onetwo.domain.store.external.data.response.DataSeoulResult;
import com.blackdog.onetwo.utils.JsonUtil;
import com.blackdog.onetwo.utils.VerifyUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataSeoulListClient {

    private final RestTemplate restTemplate;
    private final String DATA_SEOUL_URL = "http://openapi.seoul.go.kr:8088";
    private final String key = "4642796e69646f7139306f434a4b43";
    private final String returnType = "json";
    private final String dataId = "LOCALDATA_072404";


    // TODO 영업중, 인허가일자 8개월 이내 등록
    public List<DataSeoulResult> getDataSeoulResult(int start, int end) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();

            URI uri = new URIBuilder(DATA_SEOUL_URL)
                    .setPathSegments(key, returnType, dataId, String.valueOf(start), String.valueOf(end))
                    .build();

            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity(httpHeaders), String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("StatusCode = " + response.getStatusCode().value());
            }

            try {
                JsonNode jsonNode = JsonUtil.fromJson(response.getBody(), JsonNode.class).get(dataId).get("row");
                ArrayNode arrayNode = (ArrayNode) jsonNode;
                List<DataSeoulResult> results = new ArrayList<>();
                for (JsonNode node : arrayNode) {
                    results.add(DataSeoulResult.of(JsonUtil.fromJson(node.toString(), DataSeoul.class)));
                }
                return results;
            } catch (Exception e) {
                throw e;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
