package com.blackdog.onetwo.domain.common.order;

import javax.validation.GroupSequence;

@GroupSequence({OrderFirst.class, OrderSecond.class})
public interface Order {

}
