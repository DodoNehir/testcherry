package com.example.testcherry.dto;

import com.example.testcherry.domain.Member;

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
