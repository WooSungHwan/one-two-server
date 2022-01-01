package com.blackdog.onetwo.domain.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "stores",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_stores_management_id", columnNames = {"management_id"})
        })
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 관리번호
     */
    @Column(name = "management_id", length = 50)
    private String managementId;

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
}
