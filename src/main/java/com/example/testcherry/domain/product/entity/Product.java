package com.example.testcherry.domain.product.entity;

import com.example.testcherry.domain.product.dto.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PRODUCTS")
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  private Integer quantity;

  @NotNull
  private Integer price;

  @NotBlank
  private String imagePath;


  public Product(String name, String description, Integer quantity, Integer price, String imagePath) {
    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.price = price;
    this.imagePath = imagePath;
  }

  public static Product of(ProductDto productDto) {
    return new Product(productDto.name(),
        productDto.description(),
        productDto.quantity(),
        productDto.price(),
        productDto.imagePath());
  }

  public void adjustStockPlus(Integer quantity) {
    if (quantity < 0) {
      throw new RuntimeException("음수의 재고를 더할 수 없습니다.");
    }
    this.quantity += quantity;
  }

  public void adjustStockMinus(Integer quantity) {
    if (this.quantity < quantity) {
      throw new RuntimeException("재고보다 많을 수 없습니다.");
    }
    this.quantity -= quantity;
  }

  public void update(ProductDto productDto) {
    if (!productDto.name().isBlank()) {
      this.name = productDto.name();
    }
    if (!productDto.description().isBlank()) {
      this.description = productDto.description();
    }
    if (productDto.quantity() != null) {
      if (productDto.quantity() >= 0) {
        this.quantity = productDto.quantity();
      }
    }
  }

  public static Product createTestProduct(String name, String description, Integer quantity, Integer price, String imagePath) {
    return new Product(name, description, quantity, price, imagePath);
  }
  public static Product createTestProduct(String name, String description, Integer quantity) {
    return new Product(name, description, quantity, 10000, "/images/product_1.jpg");
  }
  public static Product createTestProduct(String name, String description) {
    return new Product(name, description, 1000, 10000, "/images/product_1.jpg");
  }
  public static Product createTestProduct(Integer quantity) {
    return new Product("test entity", "test description", quantity, 10000, "/images/product_1.jpg");
  }
  public static Product createTestProduct() {
    return new Product("test entity", "test description", 1000, 10000, "/images/product_1.jpg");
  }
}