package com.example.testcherry.dto;

import com.example.testcherry.domain.Order;
import com.example.testcherry.domain.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDto(
    Long memberId,
    Map<Long, Integer> quantityOfProducts // < product id , 개수 >
) {

  public static OrderDto from(Order order) {

    Map<Long, Integer> quantityOfProducts = new HashMap<>();
    for (OrderItem orderItem : order.getOrderItems()) {
      quantityOfProducts.put(orderItem.getProductId(), orderItem.getOrderQuantity());
    }

    return new OrderDto(
        order.getMemberId(),
        quantityOfProducts
    );
  }

}
