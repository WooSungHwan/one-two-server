package com.blackdog.onetwo.domain.store.entity;

import com.blackdog.onetwo.domain.common.BaseEntity;
import com.blackdog.onetwo.domain.store.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "stores",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_stores_management_id", columnNames = {"management_id"})
        })
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 관리번호
     */
    @Column(name = "management_id", length = 50)
    private String managementId;

    /**
     * 가게 이름
     */
    @Column(name = "store_name", length = 100)
    private String storeName;

    /**
     * 인허가 일자
     */
    @Column(name = "approval_date", length = 8)
    private String approvalDate;

    /**
     * 지번 주소
     */
    @Column(name = "jibun_address", length = 300)
    private String jibunAddress;

    /**
     * 도로명 주소
     */
    @Column(name = "road_address", length = 300)
    private String roadAddress;

    /**
     * 업태
     */
    @Column(name = "business_item", length = 100)
    private String businessItem;

    /**
     * 영업상태
     */
    @Column(name = "status", length = 20)
    private String status;

    /**
     * x 좌표
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * y 좌표
     */
    @Column(name = "longitude")
    private Double longitude;

    public static Store of(Long id, String managementId, String storeName, String approvalDate, String jibunAddress, String roadAddress, String businessItem, String status, Double latitude, Double longitude) {
        return new Store(id, managementId, storeName, approvalDate, jibunAddress, roadAddress, businessItem, status, latitude, longitude);
    }

    public static Store of(String managementId, String storeName, String approvalDate, String jibunAddress, String roadAddress, String businessItem, String status, Double latitude, Double longitude) {
        return of(null, managementId, storeName, approvalDate, jibunAddress, roadAddress, businessItem, status, latitude, longitude);
    }
}
