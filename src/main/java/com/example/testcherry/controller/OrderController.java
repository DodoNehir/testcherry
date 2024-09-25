package com.example.testcherry.controller;

import com.example.testcherry.domain.Response;
import com.example.testcherry.dto.OrderDto;
import com.example.testcherry.service.OrderService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/api/v1/order")
//  public Response<Void> createOrder(@RequestBody OrderDto orderDto) {
  // 일단 하드코딩해서 확인하기
  public Response<Void> createOrder() {

    Map<Long, Integer> orderMap = new HashMap<>();
    orderMap.put(1L, 1);
    orderMap.put(2L, 2);

    OrderDto orderDto = new OrderDto(2L, orderMap);

    orderService.newOrder(orderDto);

    return Response.success(null);
  }

}
