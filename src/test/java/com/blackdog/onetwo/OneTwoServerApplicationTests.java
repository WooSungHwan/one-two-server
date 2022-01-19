package com.blackdog.onetwo;

import com.blackdog.onetwo.domain.store.StoreStatus;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.store.external.data.DataSeoulClient;
import com.blackdog.onetwo.domain.store.external.data.DataSeoulListClient;
import com.blackdog.onetwo.domain.store.external.data.response.DataSeoulResult;
import com.blackdog.onetwo.domain.store.external.kakao.KakaoAddressClient;
import com.blackdog.onetwo.domain.store.external.kakao.response.AddressResult;
import com.blackdog.onetwo.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class OneTwoServerApplicationTests {

    @Autowired
    private DataSeoulClient dataSeoulClient;

    @Autowired
    private DataSeoulListClient dataSeoulListClient;

    @Autowired
    private KakaoAddressClient kakaoAddressClient;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void contextLoads() {
//        extracted();
    }

    private void extracted() {
        List<DataSeoulResult> dataSeoulResult = dataSeoulListClient.getDataSeoulResult(1, 1000);

        dataSeoulResult.forEach(data -> {
            LocalDate dateApprovalDate = LocalDate.parse(data.getApprovalDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (!data.getDetailState().equalsIgnoreCase("폐업") && LocalDate.now().minusYears(1).isBefore(dateApprovalDate)) {
                AddressResult addressResult = kakaoAddressClient.addressToLocation(data.getJibunAddress());
                Double longitude = addressResult.getX();
                Double latitude = addressResult.getY();

                if (latitude != null && longitude != null) {
                    Store store = Store.of(data.getManagementId(),
                            data.getStoreName(),
                            data.getApprovalDate(),
                            data.getJibunAddress(),
                            data.getRoadAddress(),
                            data.getBusinessItem(),
                            data.getDetailState(),
                            latitude,
                            longitude);

                    storeRepository.save(store);
                }
            }
        });

//        DataSeoulResult dataSeoulResult1 = dataSeoulResult.get(3);
//        DataSeoulResult result = dataSeoulClient.getDataSeoulResult(dataSeoulResult1.getManagementId());
//        System.out.println(result);
//        AddressResult addressResult = kakaoAddressClient.addressToLocation(dataSeoulResult1.getJibunAddress());
//        System.out.println(addressResult);
    }

}
