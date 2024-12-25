package com.example.testcherry.domain.order.service;

import com.example.testcherry.domain.member.service.MemberService;
import com.example.testcherry.exception.OrderNotFoundException;
import com.example.testcherry.exception.OutOfStockException;
import com.example.testcherry.domain.order.dto.OrderDto;
import com.example.testcherry.domain.member.entity.Member;
import com.example.testcherry.domain.order.entity.Order;
import com.example.testcherry.domain.order_item.entity.OrderItem;
import com.example.testcherry.domain.product.entity.Product;
import com.example.testcherry.domain.member.UserDetailsImpl;
import com.example.testcherry.domain.order.dto.OrderRequestBody;
import com.example.testcherry.domain.order_item.dto.OrderItemRequestBody;
import com.example.testcherry.domain.order_item.repository.OrderItemRepository;
import com.example.testcherry.domain.order.repository.OrderRepository;
import com.example.testcherry.domain.product.service.ProductService;
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
  private final MemberService memberService;

  public OrderService(OrderRepository orderRepository,
      OrderItemRepository orderItemRepository,
      ProductService productService,
      MemberService memberService) {
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
    this.productService = productService;
    this.memberService = memberService;
  }

  //  public OrderResponseBody createOrder(OrderRequestBody orderRequestBody, Member member) {
  public OrderDto createOrder(OrderRequestBody orderRequestBody, UserDetailsImpl userDetails) {

    Member member = memberService.findMemberByUsername(userDetails.getUsername());

//    Set<OrderItemResponseBody> responseBodySet = new HashSet<>();
    Order order = new Order(member);
    orderRepository.save(order);

    orderRequestBody.orderItemRequestBodySet().forEach(orderItemRequestBody ->
        createAndAddOrderItem(order, orderItemRequestBody)
    );

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

  private void createAndAddOrderItem(Order order, OrderItemRequestBody requestBody) {
    Long productId = requestBody.productId();
    Integer buyQuantity = requestBody.quantity();

    Product product = productService.checkStock(productId);
    if (product.getQuantity() < buyQuantity) {
      throw new OutOfStockException(productId);
    }

    product.adjustStockMinus(buyQuantity);

    OrderItem orderItem = new OrderItem(product, buyQuantity);

    // DB 의 N 쪽이 주인이 된다. (N 쪽이 FK)
    // 관계의 주인인 orderItem 이 order 를 수정하는 코드를 작성해야 한다.
    // 두 객체에 모두 명시적으로 반영

    order.addOrderItem(orderItem);

//      responseBodySet.add(new OrderItemResponseBody(productId, buyQuantity));
  }
}
