package com.example.testcherry.domain;

import com.example.testcherry.dto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "members")
public class Member {

  @Id
  private Long memberId;

  @NotBlank(message = "이름을 입력해주세요")
  private String name;

  @NotBlank(message = "주소를 입력해주세요")
  private String address;

  @NotBlank(message = "전화번호를 입력해주세요")
  private String phoneNumber;

  public Member(String name, String address, String phoneNumber) {
    this.name = name;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public static Member of(MemberDto memberDto) {
    return new Member(memberDto.name(),
        memberDto.address(),
        memberDto.phoneNumber());
  }

}
