package com.example.testcherry.domain;

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

  private String name;

}
