package com.example.testcherry.domain.product.repository;

import com.example.testcherry.domain.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByName(String name);

  Optional<List<Product>> findAllByNameContaining(String name);

}
