package com.example.testcherry.exception;

public class OutOfStockException extends RuntimeException {

  public OutOfStockException() {
    super("Out of stock");
  }

  public OutOfStockException(Long productId) {
    super("product id " + productId + " is out of stock");
  }

}
