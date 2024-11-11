package com.example.testcherry.repository;

import com.example.testcherry.model.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByName(String name);

  Optional<List<Product>> findAllByNameContaining(String name);

}
