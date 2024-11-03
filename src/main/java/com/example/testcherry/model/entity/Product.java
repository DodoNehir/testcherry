package com.example.testcherry.model.entity;

import com.example.testcherry.model.dto.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

  public Product(String name, Integer quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public Product(String name, String description, Integer quantity) {
    this.name = name;
    this.description = description;
    this.quantity = quantity;
  }

  public static Product of(ProductDto productDto) {
    return new Product(productDto.name(),
        productDto.description(),
        productDto.quantity());
  }

  public void updateName(String newName) {
    this.name = newName;
  }

  public void updateDescription(String newDescription) {
    this.description = newDescription;
  }

  public void adjustStockPlus(Integer quantity) {
    if (quantity < 0) {
      throw new RuntimeException("음수를 추가할 수 없습니다.");
    }
    this.quantity += quantity;
  }

  public void adjustStockMinus(Integer quantity) {
    if (this.quantity < quantity) {
      throw new RuntimeException("재고보다 많을 수 없습니다.");
    }
    this.quantity -= quantity;
  }

}