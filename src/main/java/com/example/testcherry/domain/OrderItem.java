package com.example.testcherry.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

  @Id
  private Long orderItemId;

  private Long productId;

  private Long orderId;

  private Integer orderQuantity;

  public OrderItem(Long productId, Integer orderQuantity) {
    this.productId = productId;
    this.orderQuantity = orderQuantity;
  }

}
