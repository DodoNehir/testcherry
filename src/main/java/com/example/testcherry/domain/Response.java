package com.example.testcherry.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Response<T> {

  private final HttpStatus code;
  private final String message;
  private final T data;

  public static final String CODE_SUCCESS = "success";

  public Response(HttpStatus code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> Response<T> success(T data) {
    return new Response<>(HttpStatus.OK, CODE_SUCCESS, data);
  }

  public static <T> Response<T> fail(String errorMessage) {
    return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
  }
}
