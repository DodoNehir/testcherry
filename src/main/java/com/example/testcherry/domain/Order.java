package com.example.testcherry.domain;

import com.example.testcherry.dto.OrderDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDERS")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  private Long memberId;

  private LocalDateTime orderDate;

  // order와 order item이 연결되어 있다는 것을 알린다.
//  @MappedCollection(idColumn = "order_id", keyColumn = "order_item_id")
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<OrderItem> orderItems = new HashSet<>();


  public Order(Long memberId, Set<OrderItem> orderItems) {
    this.memberId = memberId;
    this.orderDate = LocalDateTime.now();
    this.orderItems = orderItems;

    for (OrderItem orderItem : orderItems) {
      orderItem.setOrder(this);
    }
  }

  public static Order of(OrderDto orderDto) {
    Set<OrderItem> orderItems = new HashSet<>();

    for (Map.Entry<Long, Integer> entry : orderDto.quantityOfProducts().entrySet()) {
      orderItems.add(new OrderItem(entry.getKey(), entry.getValue()));
    }

    Order order = new Order(orderDto.memberId(), orderItems);

    for (OrderItem orderItem : orderItems) {
      orderItem.setOrder(order);
    }

    return order;
  }


}