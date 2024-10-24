package com.example.testcherry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId;

  private Long productId;

  private Integer orderQuantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;  // Order 객체를 통해 외래 키 관계를 설정

  public OrderItem(Long productId, Integer orderQuantity, Order order) {
    this.productId = productId;
    this.orderQuantity = orderQuantity;
    this.order = order;
  }

  public OrderItem(Long productId, Integer orderQuantity) {
    this.orderItemId = productId;
    this.orderQuantity = orderQuantity;
  }

  public void updateQuantity(Integer quantity) {
    this.orderQuantity += quantity;
  }

  protected void setOrder(Order order) {
    this.order = order;
  }

}
