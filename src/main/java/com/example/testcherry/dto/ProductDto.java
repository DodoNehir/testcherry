package com.example.testcherry.dto;

import com.example.testcherry.domain.Product;

public record ProductDto(
    String name,
    String description,
    Long quantity) {

  public static ProductDto from(Product product) {
    return new ProductDto(
        product.getName(),
        product.getDescription(),
        product.getQuantity()
    );
  }


}
