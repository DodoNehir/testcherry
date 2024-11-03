package com.example.testcherry.model.order;

import com.example.testcherry.model.order_item.OrderItemRequestBody;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record OrderRequestBody(
    @NotEmpty(message = "상품을 담아주세요")
    Set<OrderItemRequestBody> orderItemRequestBodySet
) {

}
