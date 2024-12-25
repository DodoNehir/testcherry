package com.example.testcherry.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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

  public static <T> Response<T> fail(HttpStatus httpStatus, String errorMessage) {
    return new Response<>(httpStatus, errorMessage, null);
  }
}
