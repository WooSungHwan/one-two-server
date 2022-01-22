package com.blackdog.onetwo.domain.user.request;

import com.blackdog.onetwo.domain.user.enums.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AddUserTastesParam {

    @NotNull(message = "성별을 선택해주세요.")
    private GenderStep genderStep;

    @NotNull(message = "가격대 중요 여부를 선택해주세요.")
    private PriceStep priceStep;

    @NotNull(message = "술 선호 여부를 선택해주세요.")
    private AlcoholStep alcoholStep;

    @NotNull(message = "건강식 식사 선호 여부를 선택해주세요.")
    private FreshFoodStep freshFoodStep;

    @NotNull(message = "밥만 먹고 헤어지고 싶은지 여부를 선택해주세요.")
    private PlayStep playStep;

    @NotNull(message = "선호 시간대를 선택해주세요.")
    private TimeStep timeStep;

}
