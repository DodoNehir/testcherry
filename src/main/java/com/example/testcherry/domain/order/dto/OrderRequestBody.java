package com.example.testcherry.domain.order.dto;

import com.example.testcherry.domain.order_item.dto.OrderItemRequestBody;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record OrderRequestBody(
    @NotEmpty(message = "상품을 담아주세요")
    Set<OrderItemRequestBody> orderItemRequestBodySet
) {

}
