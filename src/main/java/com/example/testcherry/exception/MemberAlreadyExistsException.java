package com.example.testcherry.exception;

public class MemberAlreadyExistsException extends RuntimeException {

  public MemberAlreadyExistsException() {
    super("Member already exists");
  }

  public MemberAlreadyExistsException(String username) {
    super("Member with username [" + username + "] already exists");
  }

}
