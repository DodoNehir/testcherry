package com.example.testcherry.domain.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderCancelRequestBody(
    @NotNull(message = "주문 번호를 입력하세요")
    Long orderId
) {

}
