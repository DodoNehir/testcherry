package com.example.testcherry.service;

import com.example.testcherry.domain.Product;
import com.example.testcherry.dto.ProductDto;
import com.example.testcherry.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public ProductDto createProduct(ProductDto productDto) {
    Product product = Product.of(productDto);
    Product savedProduct = productRepository.save(product);
    return ProductDto.from(savedProduct);
  }

  public Long checkNumber(Long productId) {
    if (productRepository.findById(productId).isPresent()) {
      return productRepository.findById(productId).get().getQuantity();
    }
    return null;
  }

//  public Long minusQuantity() {}

}
