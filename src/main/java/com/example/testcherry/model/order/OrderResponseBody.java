package com.example.testcherry.model.order;

import com.example.testcherry.model.order_item.OrderItemResponseBody;
import java.util.Set;

public record OrderResponseBody(
    Set<OrderItemResponseBody> orderItemResponseBodySet
) {

}
