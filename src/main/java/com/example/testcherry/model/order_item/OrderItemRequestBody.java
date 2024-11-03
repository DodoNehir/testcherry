package com.example.testcherry.model.order_item;

import jakarta.validation.constraints.NotNull;

public record OrderItemRequestBody(
    @NotNull(message = "상품 아이디를 입력하세요")
    Long productId,

    @NotNull(message = "상품 구매 개수를 입력하세요")
    Integer quantity
) {

}
