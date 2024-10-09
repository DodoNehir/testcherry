package com.example.testcherry.domain;

import com.example.testcherry.dto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "MEMBERS")
public class Member {

  @Id
  private Long memberId;

  @NotBlank(message = "이름을 입력해주세요")
  private String name;

  @NotBlank(message = "주소를 입력해주세요")
  private String address;

  @NotBlank(message = "전화번호를 입력해주세요")
  private String phoneNumber;

  private boolean active;

  public Member(String name, String address, String phoneNumber) {
    this.name = name;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.active = true;
  }

  public static Member of(MemberDto memberDto) {
    return new Member(memberDto.name(),
        memberDto.address(),
        memberDto.phoneNumber());
  }

  public void delete() {
    this.active = false;
  }

  public void update(MemberDto memberDto) {
    this.name = memberDto.name();
    this.address = memberDto.address();
    this.phoneNumber = memberDto.phoneNumber();
  }

}
