package com.example.testcherry.service;

import com.example.testcherry.domain.Order;
import com.example.testcherry.dto.OrderDto;
import com.example.testcherry.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public void newOrder(OrderDto orderDto) {
    Order order = Order.of(orderDto);
    orderRepository.save(order);
  }


}
