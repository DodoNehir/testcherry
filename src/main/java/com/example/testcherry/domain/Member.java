package com.example.testcherry.domain;

import com.example.testcherry.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
@Table(name = "MEMBERS")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  @NotBlank(message = "이름을 입력해주세요")
  private String name;

  @NotBlank(message = "주소를 입력해주세요")
  private String address;

  @NotBlank(message = "전화번호를 입력해주세요")
  @Column(length = 15)
  private String phoneNumber;

  private boolean active;

  protected Member() {
    this.active = true;
  }

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
    if (!memberDto.name().isBlank()) {
      this.name = memberDto.name();
    }
    if (!memberDto.address().isBlank()) {
      this.address = memberDto.address();
    }
    if (!memberDto.phoneNumber().isBlank()) {
      this.phoneNumber = memberDto.phoneNumber();
    }
  }

}
