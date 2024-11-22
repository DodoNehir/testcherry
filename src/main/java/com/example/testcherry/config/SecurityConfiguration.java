package com.example.testcherry.config;

import com.example.testcherry.jwt.JwtExceptionFilter;
import com.example.testcherry.jwt.JwtFilter;
import com.example.testcherry.jwt.JwtUtil;
import com.example.testcherry.jwt.LoginFilter;
import com.example.testcherry.model.member.Role;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {

  private final JwtFilter jwtFilter;
  private final JwtExceptionFilter jwtExceptionFilter;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtUtil jwtUtil;

  public SecurityConfiguration(
      JwtFilter jwtFilter,
      JwtExceptionFilter jwtExceptionFilter,
      AuthenticationConfiguration authenticationConfiguration,
      JwtUtil jwtUtil) {
    this.jwtFilter = jwtFilter;
    this.jwtExceptionFilter = jwtExceptionFilter;
    this.authenticationConfiguration = authenticationConfiguration;
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    return RoleHierarchyImpl.withDefaultRolePrefix()
        .role("ADMIN").implies("MEMBER")
        .build();
  }

  @Bean
  @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
  public WebSecurityCustomizer configureH2ConsoleEnable() {
    return web -> web.ignoring()
        .requestMatchers(PathRequest.toH2Console());
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

    http.csrf(CsrfConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);
    http.formLogin(FormLoginConfigurer::disable);

    http.sessionManagement(
        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // cors
    http.cors(Customizer.withDefaults());

    // domain
    http.authorizeHttpRequests(
        (requests) -> requests
            // Swagger UI
            .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**")
            .permitAll()

            .requestMatchers("/reissue").permitAll()

            // member C: permitAll, R: ADMIN, UD: MEMBER
            .requestMatchers("/members/join", "/members/login").permitAll()
            .requestMatchers("/members/**").hasAnyRole("MEMBER")
            .requestMatchers(HttpMethod.GET, "/members/**").hasAnyRole("ADMIN")

            // product R: Permit All, CUD: ADMIN
            .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
            .requestMatchers("/products", "/products/**").hasAnyRole("ADMIN")

            // order CRUD: MEMBER
            // Order 권한 에러는 로그인/가입 할 수 있도록 Redirect
            .requestMatchers("/orders").hasAnyRole("MEMBER")

            // 모든 request에 대해
            .anyRequest().authenticated());

    // login filter
    LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration),
        jwtUtil);
    loginFilter.setFilterProcessesUrl("/members/login");

    // filter chain
    http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtFilter, LoginFilter.class)
        .addFilterBefore(jwtExceptionFilter, JwtFilter.class);

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
