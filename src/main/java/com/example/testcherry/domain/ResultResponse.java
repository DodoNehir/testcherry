package com.example.testcherry.domain;

public class ResultResponse<T> {

  private boolean success;
  private String code;
  private String message;
  private T data;

  public static final String CODE_SUCCESS = "success";
  public static final String CODE_FAIL = "fail";

  public ResultResponse() {
    this.success = false;
    this.code = null;
    this.message = null;
    this.data = null;
  }

  public ResultResponse(boolean success, String code, String message, T data) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> ResultResponse<T> success(T data) {
    return new ResultResponse<>(true, CODE_SUCCESS, null, data);
  }

  public static <T> ResultResponse<T> fail(String code, String message) {
    return new ResultResponse<>(false, CODE_FAIL, message, null);
  }
}
