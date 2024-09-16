package com.example.testcherry.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cherry {

  private Long id;

  private String name;

  private LocalDate yieldDate;

  private Long amount;

}
