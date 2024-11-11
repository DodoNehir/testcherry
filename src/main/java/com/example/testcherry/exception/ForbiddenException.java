package com.example.testcherry.exception;

public class ForbiddenException extends RuntimeException {
  public ForbiddenException() {
    super("Forbidden");
  }

}
