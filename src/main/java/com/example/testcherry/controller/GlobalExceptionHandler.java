package com.example.testcherry.controller;

import com.example.testcherry.domain.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public Response handleRuntimeException(RuntimeException e) {
    return Response.fail(e.getMessage());
  }
}
