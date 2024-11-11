package com.example.testcherry.config;

import com.example.testcherry.interceptor.CheckRoleInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  private final CheckRoleInterceptor checkRoleInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(checkRoleInterceptor)
        .addPathPatterns("/products/**")
        .addPathPatterns("/members/**")
        .addPathPatterns("/orders/**");
  }

}
