package com.example.testcherry.service;

import com.example.testcherry.exception.OutOfStockException;
import com.example.testcherry.model.dto.OrderDto;
import com.example.testcherry.model.dto.OrderItemDto;
import com.example.testcherry.model.dto.ProductDto;
import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.entity.Order;
import com.example.testcherry.model.entity.OrderItem;
import com.example.testcherry.model.entity.Product;
import com.example.testcherry.model.order.OrderRequestBody;
import com.example.testcherry.model.order_item.OrderItemRequestBody;
import com.example.testcherry.repository.OrderItemRepository;
import com.example.testcherry.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final ProductService productService;

  public OrderService(OrderRepository orderRepository,
      OrderItemRepository orderItemRepository,
      ProductService productService) {
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
    this.productService = productService;
  }

  //  public OrderResponseBody newOrder(OrderRequestBody orderRequestBody, Member member) {
  public OrderDto newOrder(OrderRequestBody orderRequestBody, Member member) {

    List<OrderItem> orderItems = new ArrayList<>();

//    Set<OrderItemResponseBody> responseBodySet = new HashSet<>();

    for (OrderItemRequestBody entry : orderRequestBody.orderItemRequestBodySet()) {
      Long productId = entry.productId();
      Integer buyQuantity = entry.quantity();

      Product product = productService.checkStock(productId);
      if (product.getQuantity() < buyQuantity) {
        throw new OutOfStockException(productId);
      }

      product.adjustStockMinus(buyQuantity);

      OrderItem orderItem = OrderItem.of(new OrderItemDto(
          ProductDto.from(product),
          buyQuantity
      ));

      orderItemRepository.save(orderItem);
      orderItems.add(orderItem);

//      responseBodySet.add(new OrderItemResponseBody(productId, buyQuantity));
    }

    Order order = new Order(member, LocalDateTime.now(), orderItems);
    orderRepository.save(order);
    return OrderDto.from(order);
//    return new OrderResponseBody(responseBodySet);
  }


}
