package com.example.testcherry.model.dto;

import com.example.testcherry.model.entity.Order;
import com.example.testcherry.model.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDto(
    @NotNull MemberDto memberDto,

    @NotNull
    LocalDateTime orderDate,

    @NotEmpty(message = "주문 정보를 입력해주세요")
    Set<OrderItemDto> orderItemDtoSet,

    boolean isCanceled
) {

  // entity 에서 dto 로 변환
  public static OrderDto from(Order order) {

    Set<OrderItemDto> orderItemDtoSet = new HashSet<>();

    for (OrderItem orderItem : order.getOrderItems()) {
      orderItemDtoSet.add((OrderItemDto.from(orderItem)));
    }

    return new OrderDto(
        MemberDto.from(order.getMember()),
        order.getOrderDate(),
        orderItemDtoSet,
        order.isCanceled()
    );
  }

}
