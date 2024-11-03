package com.example.testcherry.model.entity;

import com.example.testcherry.model.dto.OrderItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

  @OneToOne(fetch = FetchType.LAZY)
  private Product product;

  @NotNull
  private Integer orderQuantity;

  public OrderItem(Product product, Integer orderQuantity) {
    this.product = product;
    this.orderQuantity = orderQuantity;
  }

  public static OrderItem of(OrderItemDto orderItemDto) {
    return new OrderItem(
        Product.of(orderItemDto.productDto()),
        orderItemDto.orderQuantity()
    );
  }


}
