package com.example.testcherry.domain;

import com.example.testcherry.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE \"MEMBERS\" SET active = false WHERE memberId = ?")
@SQLRestriction("active = true")
@Entity
@Table(name = "MEMBERS")
public class Member implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String address;

  @NotBlank
  @Length(max = 15)
  private String phoneNumber;

  private boolean active;


  public Member(String username, String password, String address, String phoneNumber) {
    this.username = username;
    this.password = password;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.active = true;
  }

  public static Member of(MemberDto memberDto) {
    return new Member(memberDto.username(),
        memberDto.password(),
        memberDto.address(),
        memberDto.phoneNumber());
  }

  @PrePersist
  public void prePersist() {
    this.active = true;
  }

//  public void delete() {
//    this.active = false;
//  }

  public void update(MemberDto memberDto) {
    if (!memberDto.username().isBlank()) {
      this.username = memberDto.username();
    }
    if (!memberDto.address().isBlank()) {
      this.address = memberDto.address();
    }
    if (!memberDto.phoneNumber().isBlank()) {
      this.phoneNumber = memberDto.phoneNumber();
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }
}
