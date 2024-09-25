package com.example.testcherry.controller;

import com.example.testcherry.domain.Response;
import com.example.testcherry.dto.OrderDto;
import com.example.testcherry.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/api/v1/order")
  public Response<Void> createOrder(@RequestBody OrderDto orderDto) {
    orderService.newOrder(orderDto);
    return Response.success(null);
  }

}
