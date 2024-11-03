package com.example.testcherry.service;

import com.example.testcherry.model.entity.Order;
import com.example.testcherry.model.dto.OrderDto;
import com.example.testcherry.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductService productService;

  public OrderService(OrderRepository orderRepository,
      ProductService productService) {
    this.orderRepository = orderRepository;
    this.productService = productService;
  }

  public void newOrder(OrderDto orderDto) {
//    List<Product> productList = new ArrayList<>();
    // TODO: 재고 조사
//    for (OrderItemDto entry : orderDto.orderItemDtoSet()) {
//      Long productId = entry.productDto().;
//      Integer buyQuantity = entry.getValue();
//
//      Product product = productService.checkStock(productId);
//      if (product.getQuantity() < buyQuantity) {
//        throw new RuntimeException("product id " + productId + " is out of stock");
//      }
//
//      product.adjustStockMinus(buyQuantity.longValue());
//      productList.add(product);
//    }

    // 개수 충분하면 Order 저장하고 재고 개수 업데이트
    Order order = Order.of(orderDto);
    orderRepository.save(order);
//    productService.saveAll(productList);
  }


}
