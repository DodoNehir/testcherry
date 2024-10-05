package com.example.testcherry.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "ORDER_ITEMS")
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

  public void updateQuantity(Integer quantity) {
    this.orderQuantity += quantity;
  }

}
