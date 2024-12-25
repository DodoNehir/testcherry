package com.example.testcherry.domain.order.repository;

import com.example.testcherry.domain.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByMemberUsername(String username);

}
