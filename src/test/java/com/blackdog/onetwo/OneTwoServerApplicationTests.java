package com.blackdog.onetwo;

import com.blackdog.onetwo.domain.store.external.data.DataSeoulClient;
import com.blackdog.onetwo.domain.store.external.data.DataSeoulListClient;
import com.blackdog.onetwo.domain.store.external.data.response.DataSeoulResult;
import com.blackdog.onetwo.domain.store.external.kakao.KakaoAddressClient;
import com.blackdog.onetwo.domain.store.external.kakao.response.AddressResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OneTwoServerApplicationTests {

    @Autowired
    private DataSeoulClient dataSeoulClient;

    @Autowired
    private DataSeoulListClient dataSeoulListClient;

    @Autowired
    private KakaoAddressClient kakaoAddressClient;

    @Test
    void contextLoads() {
//        extracted();
    }

    private void extracted() {
        List<DataSeoulResult> dataSeoulResult = dataSeoulListClient.getDataSeoulResult(1, 1000);
        System.out.println(dataSeoulResult);
        DataSeoulResult dataSeoulResult1 = dataSeoulResult.get(3);
        DataSeoulResult result = dataSeoulClient.getDataSeoulResult(dataSeoulResult1.getManagementId());
        System.out.println(result);
        AddressResult addressResult = kakaoAddressClient.addressToLocation(dataSeoulResult1.getJibunAddress());
        System.out.println(addressResult);
    }

}
