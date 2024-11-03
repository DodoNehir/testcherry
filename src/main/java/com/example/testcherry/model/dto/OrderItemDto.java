package com.example.testcherry.model.dto;

import com.example.testcherry.model.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemDto(

    @NotNull
    ProductDto productDto,

    @NotNull
    Integer orderQuantity
) {


  public static OrderItemDto from(OrderItem orderItem) {
    return new OrderItemDto(
        ProductDto.from(orderItem.getProduct()),
        orderItem.getOrderQuantity()
    );
  }
}
