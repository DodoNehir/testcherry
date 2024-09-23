package com.example.testcherry.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table
public class Member {
  @Id
  private Long id;

  @NotBlank(message = "name을 입력해주세요")
  private String name;

  @Min(10)
  @Max(99)
  private Integer age;

  @Email
  private String email;

}
