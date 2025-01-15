package com.example.testcherry.domain.product.service;

import com.example.testcherry.exception.ProductNotFoundException;
import com.example.testcherry.domain.product.dto.ProductDto;
import com.example.testcherry.domain.product.entity.Product;
import com.example.testcherry.domain.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    Optional<List<Product>> productList = productRepository.findAllByNameContaining(name);

    return productList.map(products -> products.stream().map(ProductDto::from).toList())
        .orElseGet(ArrayList::new);
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
