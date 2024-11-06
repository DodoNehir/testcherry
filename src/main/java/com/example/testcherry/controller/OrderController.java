package com.example.testcherry.controller;

import com.example.testcherry.model.dto.OrderDto;
import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.entity.Response;
import com.example.testcherry.model.order.OrderRequestBody;
import com.example.testcherry.model.order.OrderResponseBody;
import com.example.testcherry.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public Response<OrderDto> createOrder(@Valid @RequestBody OrderRequestBody orderRequestBody,
      Authentication authentication) {

    OrderDto orderResponseBody = orderService.newOrder(orderRequestBody,
        (Member) authentication.getPrincipal());
    return Response.success(orderResponseBody);
  }

}
