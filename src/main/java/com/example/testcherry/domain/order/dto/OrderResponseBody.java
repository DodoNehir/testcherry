package com.example.testcherry.domain.order.dto;

import com.example.testcherry.domain.order_item.dto.OrderItemResponseBody;
import java.util.Set;

public record OrderResponseBody(
    Set<OrderItemResponseBody> orderItemResponseBodySet
) {

}
