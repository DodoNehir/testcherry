package com.example.testcherry.repository;

import com.example.testcherry.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {


}
