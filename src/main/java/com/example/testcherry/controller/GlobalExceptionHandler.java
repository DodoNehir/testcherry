package com.example.testcherry.controller;

import com.example.testcherry.domain.Response;
import com.example.testcherry.exception.MemberNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(JwtException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Response<Void> handleSignatureException(JwtException e) {
    return Response.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
  }

  @ExceptionHandler(MemberNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Response<Void> handleMemberNotFoundException(MemberNotFoundException e) {
    return Response.fail(HttpStatus.NOT_FOUND, e.getMessage());
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public Response<Void> handleRuntimeException(RuntimeException e) {
    return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }
}
