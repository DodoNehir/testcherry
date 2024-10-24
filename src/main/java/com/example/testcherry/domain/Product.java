package com.example.testcherry.domain;

import com.example.testcherry.dto.ProductDto;
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

  @NotBlank(message = "이름을 입력해주세요")
  private String name;

  private String description;

  @NotNull(message = "개수를 입력해주세요")
  private Long quantity;

  public Product(String name, Long quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public Product(String name, String description, Long quantity) {
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

  public void adjustStockPlus(Long quantity) {
    if (quantity < 0) {
      throw new RuntimeException("음수를 추가할 수 없습니다.");
    }
    this.quantity += quantity;
  }

  public void adjustStockMinus(Long quantity) {
    if (this.quantity < quantity) {
      throw new RuntimeException("재고보다 많을 수 없습니다.");
    }
    this.quantity -= quantity;
  }

}