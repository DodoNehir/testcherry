package com.example.testcherry.service;

import com.example.testcherry.exception.ProductNotFoundException;
import com.example.testcherry.model.dto.ProductDto;
import com.example.testcherry.model.entity.Product;
import com.example.testcherry.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public ProductDto registerProduct(ProductDto productDto) {
    Product product = Product.of(productDto);
    Product savedProduct = productRepository.save(product);
    return ProductDto.from(savedProduct);
  }

  // 엔티티 반환함
  public Product checkStock(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
  }

  public List<ProductDto> findAll() {
    return productRepository.findAll().stream().map(ProductDto::from).toList();
  }

  public ProductDto findProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    return ProductDto.from(product);
  }

  public List<ProductDto> findAllByNameContaining(String name) {
    return productRepository.findAllByNameContaining(name)
        .orElseThrow(() -> new ProductNotFoundException(name))
        .stream().map(ProductDto::from).toList();
  }

  public void updateProductById(Long id, ProductDto productDto) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    product.update(productDto);
    productRepository.save(product);
  }

  public void deleteProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    productRepository.delete(product);
  }

}
