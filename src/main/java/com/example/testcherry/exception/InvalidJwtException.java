package com.example.testcherry.exception;

import io.jsonwebtoken.JwtException;

public class InvalidJwtException extends JwtException {

  public InvalidJwtException() {
    super("invalid jwt token");
  }

  public InvalidJwtException(String category) {
    super("invalid " + category + " token");
  }

}
