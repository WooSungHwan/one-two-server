package com.blackdog.onetwo.domain.common.pagination;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PageParam {
    @Min(value = 1, message = "페이지 번호를 {value}보다 크게 입력해주세요.")
    @NotNull(message = "페이지 번호를 입력해주세요.")
    private Integer page;

    @Range(min = 1, max = 20, message = "목록 행수를 {min} ~ {max}으로 입력해주세요.")
    @NotNull(message = "목록 행수를 입력해주세요.")
    private Integer limit;

    public Integer getOffset() {
        return (this.page - 1) * this.limit;
    }
}
