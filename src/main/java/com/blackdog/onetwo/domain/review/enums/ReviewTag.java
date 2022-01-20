package com.blackdog.onetwo.domain.review.enums;

import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewTag implements BaseEnum {

    NO_KIDS_ZONE("NO_KIDS_ZONE", "노키즈 존이에요"),
    WELCOME_KIDS("WELCOME_KIDS", "아이들을 환영해요"),
    TOO_EXPENSIVE("TOO_EXPENSIVE", "가격이 부담돼요"),
    CHEAP("CHEAP", "가격이 저렴해요"),
    NO_RESERVATION("NO_RESERVATION", "예약이 안돼요"),
    RESERVATION("RESERVATION", "예약 가능해요"),
    WELCOME_PETS("WELCOME_PETS", "반려동물을 환영해요"),
    DARK_LIGHTS("DARK_LIGHTS", "조명이 어두워요"),
    SONG_LOUD("SONG_LOUD", "노래가 시끄러워요"),
    ALCOHOLIC_BEVERAGE_REQUIRED("ALCOHOLIC_BEVERAGE_REQUIRED", "주류주문이 필수에요"),
    NO_TOILET("NO_TOILET", "화장실이 없어요"),
    SHARED_TOILET("SHARED_TOILET", "화장실이 공용이에요"),
    INSIDE_TOILET("INSIDE_TOILET", "화장실이 내부에 있어요"),
    OUTSIDE_TOILET("OUTSIDE_TOILET", "화장실이 외부에 있어요"),
    SPECIAL_SONG("SPECIAL_SONG", "노래가 특별해요"),
    STORE_QUIET("STORE_QUIET", "가게가 조용해요"),
    WILL_GO_BACK("WILL_GO_BACK", "다시 갈래요"),
    DONT_GO_AGAIN("DONT_GO_AGAIN", "다신 안갈래요"),
    COFFEE_DELICIOUS("COFFEE_DELICIOUS", "커피가 맛있어요"),
    FOOD_DELICIOUS("FOOD_DELICIOUS", "음식이 맛있어요"),
    DRINK_DELICIOUS("DRINK_DELICIOUS", "음료가 맛있어요"),
    DESSERT_DELICIOUS("DESSERT_DELICIOUS", "디저트가 맛있어요"),
    NICE_INTERIOR("NICE_INTERIOR", "인테리어가 좋아요"),
    FRIENDLY_STAFF("FRIENDLY_STAFF", "직원분이 친절해요"),
    GOOD_PICTURE("GOOD_PICTURE", "사진찍기 좋아요"),
    SPECIAL_FOOD("SPECIAL_FOOD", "음식이 특별해요");

    private String type;

    private String name;

    public static ReviewTag find(String type) {
        return BaseEnum.find(type, values());
    }

    public static ReviewTag findToNull(String type) {
        return BaseEnum.findToNull(type, values());
    }

}
