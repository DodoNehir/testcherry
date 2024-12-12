package com.example.testcherry.service;

import com.example.testcherry.exception.OrderNotFoundException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
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

    logger.info("New Order");
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

  public List<OrderDto> getAllOrders(String username) {
    List<Order> orders = orderRepository.findByMemberUsername(username);
    List<OrderDto> orderDtos = new ArrayList<>();

    for (Order order : orders) {
      orderDtos.add(OrderDto.from(order));
    }
    return orderDtos;
  }

  public OrderDto cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    order.setCanceled(true);

    orderRepository.save(order);
    return OrderDto.from(order);
  }
}
