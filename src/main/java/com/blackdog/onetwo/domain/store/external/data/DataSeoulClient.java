package com.blackdog.onetwo.domain.store.external.data;

import com.blackdog.onetwo.domain.store.external.data.response.DataSeoulResult;
import com.blackdog.onetwo.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataSeoulClient {

    private final RestTemplate restTemplate;
    private final String DATA_SEOUL_URL = "https://data.seoul.go.kr/dataList/dataView.do";

    public DataSeoulResult getDataSeoulResult(String managementId) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();

            URI uri = new URIBuilder(DATA_SEOUL_URL)
                    .addParameter("onepagerow", "1000")
                    .addParameter("srvType", "S")
                    .addParameter("infId", "OA-16094")
                    .addParameter("serviceKind", "1")
                    .addParameter("pageNo", "1")
                    .addParameter("gridTotalCnt", "1")
                    .addParameter("filterCol", "MGTNO")
                    .addParameter("txtFilter", managementId)
                    .build();

            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity(httpHeaders), String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("StatusCode = " + response.getStatusCode().value());
            }

            try {
                DataSeoulInfo dataSeoulInfo = JsonUtil.fromJson(new JSONObject(response.getBody()).toString(), DataSeoulInfo.class);
                return DataSeoulResult.of(dataSeoulInfo.getManageId(), dataSeoulInfo.getJibunAddress(), dataSeoulInfo.getRoadAddress());
            } catch (Exception e) {
                throw e;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Data
    private static class DataSeoulInfo {
        private String result;
        private List<DataSeoul> list;

        private String getManageId() {
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            return list.get(0).getManagementId();
        }

        private String getJibunAddress() {
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            return list.get(0).getJibunAddress();
        }

        private String getRoadAddress() {
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            return list.get(0).getRoadAddress();
        }

        @Data
        private static class DataSeoul {
            @JsonProperty("MGTNO")
            private String managementId;
            @JsonProperty("DTLSTATENM")
            private String detailState;
            @JsonProperty("SITEWHLADDR")
            private String jibunAddress;
            @JsonProperty("RDNWHLADDR")
            private String roadAddress;
            @JsonProperty("X")
            private String x;
            @JsonProperty("Y")
            private String y;
            @JsonProperty("APVPERMYMD")
            private String approvalDate;
        }
    }
}
