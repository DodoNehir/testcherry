package com.example.testcherry.repository;

import com.example.testcherry.model.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByMemberUsername(String username);

}
