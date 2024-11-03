package com.example.testcherry.model.entity;

import com.example.testcherry.model.dto.OrderDto;
import com.example.testcherry.model.dto.OrderItemDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDERS",
    indexes = {@Index(name = "orders_member_id_idx", columnList = "member_id")})
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  private LocalDateTime orderDate;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;


  public Order(Member member, LocalDateTime orderDate, List<OrderItem> orderItems) {
    this.member = member;
    this.orderDate = orderDate;
    this.orderItems = orderItems;
  }

  // dto 에서 entity 로 변환
  public static Order of(OrderDto orderDto) {
    List<OrderItem> orderItems = new ArrayList<>();

    for (OrderItemDto entry : orderDto.orderItemDtoSet()) {
      orderItems.add(OrderItem.of(entry));
    }

    return new Order(
        Member.of(orderDto.memberDto()),
        orderDto.orderDate(),
        orderItems);
  }

  @PrePersist
  public void prePersist() {
    orderDate = LocalDateTime.now();
  }

}