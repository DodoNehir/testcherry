package com.example.testcherry.exception;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException() {
    super("Order not found");
  }

  public OrderNotFoundException(Long orderId) {
    super("Order with Id " + orderId + " not found");
  }

}
