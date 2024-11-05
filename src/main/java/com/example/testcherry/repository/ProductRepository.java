package com.example.testcherry.repository;

import com.example.testcherry.model.entity.Product;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

  Optional<Product> findByName(String name);

}
