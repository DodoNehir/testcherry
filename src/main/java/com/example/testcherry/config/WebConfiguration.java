package com.example.testcherry.config;

import com.example.testcherry.model.member.Role;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtExceptionFilter jwtExceptionFilter;

  public WebConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
      JwtExceptionFilter jwtExceptionFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.jwtExceptionFilter = jwtExceptionFilter;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
    configuration.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .authorizeHttpRequests(
            (requests) -> requests
                // member C: permitAll, R: ADMIN, UD: indivisual & ADMIN
                .requestMatchers(HttpMethod.POST, "/members", "/members/authenticate")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/members")
                .hasRole(Role.ADMIN.name())
                .requestMatchers("/members")
                .hasRole(Role.USER.name())

                // product R: Permit All, CUD: ADMIN
                .requestMatchers(HttpMethod.GET, "/products", "/products/all", "/products/name/")
                .permitAll()
                .requestMatchers("/products")
                .hasRole(Role.ADMIN.name())

                .anyRequest().authenticated()) // 모든 request에 대해
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // session은 생성되지 않도록
        .csrf(CsrfConfigurer::disable) // csrf는 disable
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass())
        .httpBasic(Customizer.withDefaults()); // basic auth는 사용

    return http.build();
  }

//  @Bean
//  public TransactionTemplate writeTransactionOperations(
//      PlatformTransactionManager transactionManager) {
//    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
//    transactionTemplate.setReadOnly(false);
//    return transactionTemplate;
//  }
//
//  @Bean
//  public TransactionTemplate readTransactionOperations(
//      PlatformTransactionManager transactionManager) {
//    TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
//    transactionTemplate.setReadOnly(true);
//    return transactionTemplate;
//  }

}
