package com.example.testcherry.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException() {
    super("Product not found");
  }

  public ProductNotFoundException(String name) {
    super("Product with name " + name + " not found");
  }

  public ProductNotFoundException(Long id) {
    super("Product with id " + id + " not found");
  }

}
