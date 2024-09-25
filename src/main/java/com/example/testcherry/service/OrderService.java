package com.example.testcherry.service;

import com.example.testcherry.domain.Order;
import com.example.testcherry.domain.Product;
import com.example.testcherry.dto.OrderDto;
import com.example.testcherry.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    List<Product> productList = new ArrayList<>();
    // 재고 조사
    for (Map.Entry<Long, Integer> entry : orderDto.quantityOfProducts().entrySet()) {
      Long productId = entry.getKey();
      Integer buyQuantity = entry.getValue();

      Product product = productService.checkStock(productId);
      if (product.getQuantity() < buyQuantity) {
        throw new RuntimeException("product id " + productId + " is out of stock");
      }

      product.adjustStockMinus(buyQuantity.longValue());
      productList.add(product);
    }

    // 개수 충분하면 Order 저장하고 재고 개수 업데이트
    Order order = Order.of(orderDto);
    orderRepository.save(order);
    productService.saveAll(productList);
  }


}
