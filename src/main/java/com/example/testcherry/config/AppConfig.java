package com.example.testcherry.config;

import com.example.testcherry.repository.JdbcMemberRepository;
import com.example.testcherry.repository.MemberRepository;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  private DataSource dataSource;

  public AppConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public MemberRepository memberRepository() {
    return new JdbcMemberRepository(dataSource);
  }

}
