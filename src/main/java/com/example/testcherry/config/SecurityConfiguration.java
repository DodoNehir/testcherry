package com.example.testcherry.config;

import com.example.testcherry.auth.handler.CustomAccessDeniedHandler;
import com.example.testcherry.auth.handler.CustomAuthenticationEntryPoint;
import com.example.testcherry.auth.handler.CustomAuthenticationSuccessHandler;
import com.example.testcherry.auth.jwt.filter.JwtAuthenticationFilter;
import com.example.testcherry.auth.jwt.filter.JwtExceptionFilter;
import com.example.testcherry.auth.jwt.util.JwtUtil;
import com.example.testcherry.domain.refresh.repository.RefreshReposiotry;
import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtExceptionFilter jwtExceptionFilter;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtUtil jwtUtil;
  private final RefreshReposiotry refreshReposiotry;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  public SecurityConfiguration(
      JwtAuthenticationFilter jwtAuthenticationFilter,
      JwtExceptionFilter jwtExceptionFilter,
      AuthenticationConfiguration authenticationConfiguration,
      JwtUtil jwtUtil,
      RefreshReposiotry refreshReposiotry,
      CustomAccessDeniedHandler customAccessDeniedHandler) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.jwtExceptionFilter = jwtExceptionFilter;
    this.authenticationConfiguration = authenticationConfiguration;
    this.jwtUtil = jwtUtil;
    this.refreshReposiotry = refreshReposiotry;
    this.customAccessDeniedHandler = customAccessDeniedHandler;
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
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(
        List.of(
//            "http://relaxed-daveta-kiraz-787c046b.koyeb.app",
            "https://relaxed-daveta-kiraz-787c046b.koyeb.app/",
            "http://localhost:8080",
            "http://127,0,0,1:8080"
        ));
    configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
    configuration.addAllowedHeader("*");
//    configuration.addExposedHeader("Authorization");
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {

    http.csrf(CsrfConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);

    http.formLogin(form -> form
        .loginPage("/login") // 인증되지 않은 사용자가 보호된 리소스로 접근할 때 /login 으로 리디렉션
        .loginProcessingUrl("/login") // 로그인 폼 제출 요청을 처리하는 URL.
        // 여기서는 UsernamePasswordAuthenticationFilter 가 처리한다.
        .successHandler(new CustomAuthenticationSuccessHandler(jwtUtil, refreshReposiotry))
        .failureUrl("/login?error"));

    http.logout(LogoutConfigurer::disable);

    http.sessionManagement(
        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // cors
    http.cors(Customizer.withDefaults());

    // domain
    http.authorizeHttpRequests(
        (requests) -> requests
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            // Swagger UI
            .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**")
            .permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            .requestMatchers(HttpMethod.POST, "/reissue").permitAll()

            .requestMatchers(HttpMethod.GET, "/").permitAll()
            .requestMatchers(HttpMethod.GET, "members/signup").permitAll()
            .requestMatchers(HttpMethod.GET, "members/checkId").permitAll()
            .requestMatchers("/login").permitAll()

//            .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()

            // members
            .requestMatchers(HttpMethod.GET, "/members/{id}").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/members/logout").hasAnyRole("MEMBER")
            .requestMatchers(HttpMethod.PATCH, "/members/**").hasAnyRole("MEMBER")
            .requestMatchers(HttpMethod.DELETE, "/members/**").hasAnyRole("MEMBER")
            .requestMatchers(HttpMethod.POST, "/members/join").permitAll()

            // products
            .requestMatchers(HttpMethod.POST, "/products").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/products/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/products/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

            // orders
            .requestMatchers(HttpMethod.PATCH, "/orders").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("MEMBER")
            .requestMatchers(HttpMethod.GET, "/orders").hasAnyRole("MEMBER")

        // 모든 request에 대해
//            .anyRequest().authenticated());
    );

    // filter chain
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

    // handler
    http.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
        httpSecurityExceptionHandlingConfigurer
            .accessDeniedHandler(customAccessDeniedHandler)
            .authenticationEntryPoint(customAuthenticationEntryPoint));

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
