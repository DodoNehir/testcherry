package com.example.testcherry.domain;

import com.example.testcherry.dto.ProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "PRODUCTS")
public class Product {

  @Id
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

}