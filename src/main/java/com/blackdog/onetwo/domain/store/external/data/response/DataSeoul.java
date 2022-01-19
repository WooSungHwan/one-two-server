package com.blackdog.onetwo.domain.store.external.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataSeoul {
    @JsonProperty("MGTNO")
    private String manageId; // 가게 아이디
    @JsonProperty("BPLCNM")
    private String storeName; // 가게명
    @JsonProperty("UPTAENM")
    private String businessItem; // 일식, 분식, 한식
    @JsonProperty("DTLSTATENM")
    private String detailState; // 영업, 폐업
    @JsonProperty("SITEWHLADDR")
    private String jibunAddress; // 지번주소
    @JsonProperty("RDNWHLADDR")
    private String roadAddress; // 도로명주소
    @JsonProperty("X")
    private String x; // x좌표(안씀)
    @JsonProperty("Y")
    private String y; // y좌표(안씀)
    @JsonProperty("APVPERMYMD")
    private String approvalDate; // 인허가일자
}
