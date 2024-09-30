package com.example.testcherry.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.testcherry.domain.Product;
import com.example.testcherry.dto.OrderDto;
import com.example.testcherry.repository.OrderRepository;
import java.util.HashMap;
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
  ProductService productService;

  @InjectMocks
  OrderService orderService;

  @Test
  @DisplayName("재고가 있을 때의 정상주문")
  public void stockQuantityTest_success() {
    // given
    Long stockQuantity = 100L;
    int buyQuantity = 50;
    HashMap<Long, Integer> orderMap = new HashMap<>();
    orderMap.put(1L, buyQuantity);

    // newOrder method 호출 때 필요한 것들
    OrderDto orderDto = new OrderDto(1L, orderMap);
    Product product = new Product("sajo", stockQuantity);
    when(productService.checkStock(1L)).thenReturn(product);

    // when
    orderService.newOrder(orderDto);

    // then
    assertThat(product.getQuantity()).isEqualTo(stockQuantity - buyQuantity);
  }

  @Test
  @DisplayName("재고가 없을 때 예외발생")
  public void stockQuantityTest_failure() {
    // given
    Long stockQuantity = 10L;
    int buyQuantity = 100;
    HashMap<Long, Integer> orderMap = new HashMap<>();
    Long productId = 1L;
    orderMap.put(productId, buyQuantity);

    OrderDto orderDto = new OrderDto(1L, orderMap);
    Product product = new Product("sajo", stockQuantity);
    when(productService.checkStock(1L)).thenReturn(product);

    // when & then
    RuntimeException runtimeException = assertThrows(RuntimeException.class,
        () -> orderService.newOrder(orderDto));
    assertThat(runtimeException.getMessage()).isEqualTo(
        "product id " + productId + " is out of stock");
  }

}