package com.example.testcherry.model.order;

import jakarta.validation.constraints.NotNull;

public record OrderCancelRequestBody(
    @NotNull(message = "주문 번호를 입력하세요")
    Long orderId
) {

}
