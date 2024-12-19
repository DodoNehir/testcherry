package com.example.testcherry.model.entity;

import com.example.testcherry.model.dto.MemberDto;
import com.example.testcherry.model.member.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SQLDelete(sql = "UPDATE \"MEMBERS\" SET active = false WHERE username = ?")
@SQLRestriction("active = true")
@Entity
@Table(name = "MEMBERS",
    indexes = {@Index(name = "members_username_idx", columnList = "username", unique = true)})
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  @NotBlank
  @Column(unique = true)
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String address;

  @NotBlank
  @Length(max = 15)
  private String phoneNumber;

  @Enumerated(value = EnumType.STRING)
  private Role role;

  private boolean active;


  public Member(String username, String password, String address, String phoneNumber) {
    this.username = username;
    this.password = password;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public Member(String username, String role) {
    this.username = username;
    this.password = "qwe";
    this.role = Role.valueOf(role);
    this.address = "token";
    this.phoneNumber = "123456789";
    this.active = true;
  }

//  public static Member of(MemberDto memberDto) {
//    return new Member(memberDto.username(),
//        memberDto.address(),
//        memberDto.phoneNumber());
//  }

  @PrePersist
  public void prePersist() {
    this.role = Role.ROLE_MEMBER;
    this.active = true;
  }

//  public void delete() {
//    this.active = false;
//  }


  public void delete() {
    this.active = false;
  }

  public void update(MemberDto memberDto) {
    if (!memberDto.address().isBlank()) {
      this.address = memberDto.address();
    }
    if (!memberDto.phoneNumber().isBlank()) {
      this.phoneNumber = memberDto.phoneNumber();
    }
  }


}
