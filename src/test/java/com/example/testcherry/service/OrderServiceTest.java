package com.example.testcherry.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.example.testcherry.domain.member.UserDetailsImpl;
import com.example.testcherry.domain.member.entity.Member;
import com.example.testcherry.domain.member.repository.MemberRepository;
import com.example.testcherry.domain.member.service.MemberService;
import com.example.testcherry.domain.order.dto.OrderRequestBody;
import com.example.testcherry.domain.order.repository.OrderRepository;
import com.example.testcherry.domain.order.service.OrderService;
import com.example.testcherry.domain.order_item.dto.OrderItemRequestBody;
import com.example.testcherry.domain.product.entity.Product;
import com.example.testcherry.domain.product.service.ProductService;
import com.example.testcherry.exception.OutOfStockException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock
  OrderRepository orderRepository;

  @Mock
  MemberService memberService;

  @Mock
  MemberRepository memberRepository;

  @Mock
  ProductService productService;

  @InjectMocks
  OrderService orderService;

  @Test
  @DisplayName("재고가 있을 때의 정상주문")
  public void stockQuantityTest_success() {
    // given
    Member member = new Member("qwerty", "qwerty", "qwerty", "qwerty");
    memberRepository.save(member);
    UserDetailsImpl userDetails = new UserDetailsImpl(member);

    Integer stockQuantity = 100;
    Integer buyQuantity = 30;
    Product product1 = new Product("sajo", "asdf", stockQuantity);
    Product product2 = new Product("apple", "asdf", stockQuantity);
    Product product3 = new Product("tiramisu", "asdf", stockQuantity);

    Set<OrderItemRequestBody> itemSet = new HashSet<>();
    for (int i = 1; i < 4; i++) {
      OrderItemRequestBody item = new OrderItemRequestBody(Integer.valueOf(i).longValue(),
          buyQuantity);
      itemSet.add(item);
    }

    // newOrder method 호출 때 필요한 것들
    OrderRequestBody orderRequestBody = new OrderRequestBody(itemSet);

    when(memberService.findMemberByUsername(userDetails.getUsername())).thenReturn(member);

    when(productService.checkStock(1L)).thenReturn(product1);
    when(productService.checkStock(2L)).thenReturn(product2);
    when(productService.checkStock(3L)).thenReturn(product3);

    // when
    orderService.createOrder(orderRequestBody, userDetails);

    // then
    assertThat(product1.getQuantity()).isEqualTo(stockQuantity - buyQuantity);
    assertThat(product2.getQuantity()).isEqualTo(stockQuantity - buyQuantity);
    assertThat(product3.getQuantity()).isEqualTo(stockQuantity - buyQuantity);

  }

  @Test
  @DisplayName("재고가 없을 때 예외발생")
  public void stockQuantityTest_failure() {
    // given
    Member member = new Member("qwerty", "qwerty", "qwerty", "qwerty");
    memberRepository.save(member);
    UserDetailsImpl userDetails = new UserDetailsImpl(member);

    Integer stockQuantity = 10;
    Integer buyQuantity = 30;
    Product product1 = new Product("sajo", "asdf", stockQuantity);
    Product product2 = new Product("apple", "asdf", stockQuantity);
    Product product3 = new Product("tiramisu", "asdf", stockQuantity);

    Set<OrderItemRequestBody> itemSet = new HashSet<>();
    for (int i = 1; i < 4; i++) {
      OrderItemRequestBody item = new OrderItemRequestBody(Integer.valueOf(i).longValue(),
          buyQuantity);
      itemSet.add(item);
    }

    OrderRequestBody orderRequestBody = new OrderRequestBody(itemSet);

    when(memberService.findMemberByUsername(userDetails.getUsername())).thenReturn(member);

    lenient().when(productService.checkStock(1L)).thenReturn(product1);
    lenient().when(productService.checkStock(2L)).thenReturn(product2);
    lenient().when(productService.checkStock(3L)).thenReturn(product3);

    // when & then
//    OutOfStockException outOfStockException =
    assertThrows(OutOfStockException.class,
        () -> orderService.createOrder(orderRequestBody, userDetails));

//    assertThat(runtimeException.getMessage()).isEqualTo("product id " + 1L + " is out of stock");
  }

}