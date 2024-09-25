package com.example.testcherry.domain;

import com.example.testcherry.dto.OrderDto;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order {

  @Id
  private Long orderId;

  private Long memberId;

  private LocalDateTime orderDate;

  // order와 order item이 연결되어 있다는 것을 알린다.
  @MappedCollection(idColumn = "order_item_id", keyColumn = "order_id")
  private List<OrderItem> orderItems = new ArrayList<>();

  public Order(Long memberId, List<OrderItem> orderItems) {
    this.memberId = memberId;
    this.orderDate = LocalDateTime.now();
    this.orderItems = orderItems;
  }

  public static Order of(OrderDto orderDto) {
    List<OrderItem> orderItems = new ArrayList<>();

    for (Map.Entry<Long, Integer> entry : orderDto.quantityOfProducts().entrySet()) {
      orderItems.add(new OrderItem(entry.getKey(), entry.getValue()));
    }

    return new Order(orderDto.memberId(), orderItems);
  }


}