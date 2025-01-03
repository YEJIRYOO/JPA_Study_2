package jpabook.jpashop_v2.domain;

import lombok.Getter;

@Getter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
