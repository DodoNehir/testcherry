package com.example.testcherry.exception;

public class MemberNotFoundException extends RuntimeException {

  public MemberNotFoundException() {
    super("Member not found");
  }

  public MemberNotFoundException(String username) {
    super("Member with username " + username + " not found");
  }

  public MemberNotFoundException(Long id) {
    super("Member with id " + id + " not found");
  }

}
