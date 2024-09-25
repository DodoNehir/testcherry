package com.example.testcherry.service;

import com.example.testcherry.domain.Product;
import com.example.testcherry.dto.ProductDto;
import com.example.testcherry.repository.ProductRepository;
import java.util.List;
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

  public Product checkStock(Long productId) {
    if (productRepository.findById(productId).isEmpty()) {
      throw new RuntimeException("Product with id " + productId + " does not exist");
    }
    return productRepository.findById(productId).get();
  }

  public void saveAll(List<Product> productList) {
    productRepository.saveAll(productList);
  }

}
