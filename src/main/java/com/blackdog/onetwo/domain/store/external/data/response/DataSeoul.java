package com.blackdog.onetwo.domain.store.external.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataSeoul {
    @JsonProperty("MGTNO")
    private String manageId;
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
