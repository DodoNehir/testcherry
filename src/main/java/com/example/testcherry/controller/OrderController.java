package com.example.testcherry.controller;

import com.example.testcherry.model.dto.OrderDto;
import com.example.testcherry.model.entity.Member;
import com.example.testcherry.model.entity.Response;
import com.example.testcherry.model.member.UserDetailsImpl;
import com.example.testcherry.model.order.OrderCancelRequestBody;
import com.example.testcherry.model.order.OrderRequestBody;
import com.example.testcherry.service.MemberService;
import com.example.testcherry.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Order API")
@RestController
@RequestMapping("/orders")
public class OrderController {

  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
  private final OrderService orderService;
  private final MemberService memberService;

  public OrderController(OrderService orderService, MemberService memberService) {
    this.orderService = orderService;
    this.memberService = memberService;
  }

  @Operation(summary = "주문 생성", description = "MEMBER 만 주문할 수 있습니다.")
  @PostMapping
  public Response<OrderDto> createOrder(@Valid @RequestBody OrderRequestBody orderRequestBody,
      Authentication authentication) {

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    logger.info("로그인한 유저의 Username: " + userDetails.getUsername());

    // TODO member를 여기서 넣지 않고 orderService에서 엔티티를 조회하도록 변경
    Member member = memberService.findMemberByUsername(userDetails.getUsername());

    OrderDto orderResponseBody = orderService.createOrder(orderRequestBody, member);
    return Response.success(orderResponseBody);
  }

  @Operation(summary = "주문 조회", description = "본인의 주문을 조회할 수 있습니다.")
  @GetMapping
  public Response<List<OrderDto>> getOrder(Authentication authentication) {
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//    logger.info("로그인한 유저의 Username: " + userDetails.getUsername());
    return Response.success(orderService.getAllOrders(userDetails.getUsername()));
  }

  @Operation(summary = "주문 취소", description = "ADMIN 만 취소할 수 있습니다.")
  @PatchMapping
  public Response<OrderDto> cancelOrder(@RequestBody OrderCancelRequestBody orderCancelRequestBody) {
    return Response.success(orderService.cancelOrder(orderCancelRequestBody.orderId()));
  }

}
