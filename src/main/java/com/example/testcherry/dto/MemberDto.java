package com.example.testcherry.dto;

import com.example.testcherry.domain.Member;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberDto(
    String name,
    String address,
    String phoneNumber) {

  public static MemberDto from(Member member) {
    return new MemberDto(
        member.getName(),
        member.getAddress(),
        member.getPhoneNumber()
    );
  }

}
