package com.example.testcherry.model.dto;

import com.example.testcherry.model.entity.Product;
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
    Integer quantity) {

  public static ProductDto from(Product product) {
    return new ProductDto(
        product.getName(),
        product.getDescription(),
        product.getQuantity()
    );
  }


}
