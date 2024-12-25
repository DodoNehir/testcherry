package com.example.testcherry.controller;

import com.example.testcherry.exception.ForbiddenException;
import com.example.testcherry.auth.jwt.exception.InvalidJwtException;
import com.example.testcherry.exception.MemberAlreadyExistsException;
import com.example.testcherry.exception.MemberNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidJwtException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response<Void> handleInvalidJwtException(InvalidJwtException e) {
    return Response.fail(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(ExpiredJwtException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response<Void> handleExpiredJwtException(ExpiredJwtException e) {
    return Response.fail(HttpStatus.BAD_REQUEST, "expired jwt token");
  }

  @ExceptionHandler(MemberAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Response<Void> handleMemberAlreadyExistsException(MemberAlreadyExistsException e) {
    return Response.fail(HttpStatus.CONFLICT, e.getMessage());
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public Response<Void> handleForbiddenException(ForbiddenException e) {
    return Response.fail(HttpStatus.FORBIDDEN, e.getMessage());
  }

  @ExceptionHandler(MemberNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Response<Void> handleMemberNotFoundException(MemberNotFoundException e) {
    return Response.fail(HttpStatus.NOT_FOUND, e.getMessage());
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return Response.fail(HttpStatus.BAD_REQUEST, "Required request body is missing");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response<Void> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String errMsg = e.getFieldErrors().stream()
        .map(fieldError -> (fieldError.getField() + ": " + fieldError.getDefaultMessage()))
        .toList()
        .toString();
    return Response.fail(HttpStatus.BAD_REQUEST, errMsg);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response<Void> handleConstraintViolationException(
      ConstraintViolationException e) {
    String errMsg = e.getConstraintViolations().stream()
        .map(cv -> (cv.getPropertyPath() + ": " + cv.getMessage()))
        .toList()
        .toString();
    return Response.fail(HttpStatus.BAD_REQUEST, errMsg);
  }

//  @ExceptionHandler(RuntimeException.class)
//  public Response<Void> handleRuntimeException(RuntimeException e) {
//    return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//  }
}
