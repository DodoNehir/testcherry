package com.example.testcherry.model.entity;

import com.example.testcherry.model.dto.OrderDto;
import com.example.testcherry.model.dto.OrderItemDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    indexes = {@Index(name = "orders_username_idx", columnList = "username")})
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  private LocalDateTime orderDate;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  private boolean isCanceled;

  public Order(Member member) {
    this.member = member;
  }

  public Order(Member member, LocalDateTime orderDate, List<OrderItem> orderItems) {
    this.member = member;
    this.orderDate = orderDate;
    this.orderItems = orderItems;
  }


  public void addOrderItem(OrderItem orderItem) {
    this.orderItems.add(orderItem);
    orderItem.setOrder(this); // 양방향 연관관계 설정
  }

  @PrePersist
  public void prePersist() {
    orderDate = LocalDateTime.now();
    isCanceled = false;
  }

}