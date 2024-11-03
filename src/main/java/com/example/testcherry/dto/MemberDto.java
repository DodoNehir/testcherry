package com.example.testcherry.dto;

import com.example.testcherry.domain.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberDto(

    @NotBlank(message = "이름을 입력해주세요")
    String username,

    @NotBlank(message = "비밀번호를 입력해주세요")
    String password,

    @NotBlank(message = "주소를 입력해주세요")
    String address,

    @Length(max = 15)
    @NotBlank(message = "전화번호를 입력해주세요")
    String phoneNumber) {

  public static MemberDto from(Member member) {
    return new MemberDto(
        member.getUsername(),
        member.getPassword(),
        member.getAddress(),
        member.getPhoneNumber()
    );
  }

}
