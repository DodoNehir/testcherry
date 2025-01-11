package com.example.testcherry.domain.product.dto;

import com.example.testcherry.domain.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDto(
    @NotBlank(message = "상품 이름을 입력해주세요")
    String name,

    @NotBlank(message = "상품 설명을 입력해주세요")
    String description,

    @Min(0)
    @NotNull(message = "상품 재고를 입력해주세요")
    Integer quantity,

    @Min(1000)
    @NotNull(message = "상품 가격을 입력해주세요")
    Integer price,

    @NotBlank(message = "상품 이미지 path를 입력해주세요")
    String imagePath) {

  public static ProductDto from(Product product) {
    return new ProductDto(
        product.getName(),
        product.getDescription(),
        product.getQuantity(),
        product.getPrice(),
        product.getImagePath()
    );
  }

  public static ProductDto createTestProductDto() {
    return new ProductDto("test Dto", "test description", 200, 30000, "/images/test.png");
  }
  public static ProductDto createTestProductDto(Integer quantity) {
    return new ProductDto("test Dto", "test description", quantity, 30000, "/images/test.png");
  }


}
