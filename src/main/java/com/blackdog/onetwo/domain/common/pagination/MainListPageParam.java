package com.blackdog.onetwo.domain.common.pagination;

import com.blackdog.onetwo.domain.common.order.OrderFirst;
import com.blackdog.onetwo.domain.common.order.OrderSecond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MainListPageParam {

    @Min(value = 1, message = "페이지 번호를 {value}보다 크게 입력해주세요.", groups = OrderFirst.class)
    @NotNull(message = "페이지 번호를 입력해주세요.", groups = OrderFirst.class)
    private Integer page;

    @Range(min = 5, max = 10, message = "목록 행수를 {min} ~ {max}으로 입력해주세요.", groups = OrderFirst.class)
    private Integer limit = 5;

    @Positive(message = "id는 양수로 입력해주세요.", groups = OrderSecond.class)
    private Long id;

    @JsonIgnore
    @AssertTrue(message = "첫 페이지에서는 id를 입력할 수 없습니다.", groups = OrderSecond.class)
    private boolean isValidId() {
        return this.page != 1 || Objects.isNull(this.id);
    }

    @JsonIgnore
    @AssertTrue(message = "id를 입력해주세요.", groups = OrderSecond.class)
    private boolean isIdNotNull() {
        return this.page == 1 || Objects.nonNull(this.id);
    }

}
