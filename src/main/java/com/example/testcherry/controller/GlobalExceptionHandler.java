package com.example.testcherry.controller;

import com.example.testcherry.domain.Response;
import com.example.testcherry.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MemberNotFoundException.class)
  public Response handleMemberNotFoundException(MemberNotFoundException e) {
    return Response.fail(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public Response handleRuntimeException(RuntimeException e) {
    return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }
}
