package com.example.testcherry.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "API DOCS",
        description = "Koyeb 으로 배포한 서비스의 API 문서입니다",
        version = "1.0"
    ),
    servers = @Server(
        url = "https://relaxed-daveta-kiraz-787c046b.koyeb.app"
    )
)

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    String AUTH_SCHEME_NAME = "JWT AccessToken";
    String AUTH_SCHEME = "bearer";
    String BEARER_FORMAT = "JWT";

    SecurityRequirement securityRequirement = new SecurityRequirement().addList(AUTH_SCHEME_NAME);
    Components components = new Components()
        .addSecuritySchemes(AUTH_SCHEME_NAME, new SecurityScheme()
            .name(AUTH_SCHEME_NAME)
            .type(SecurityScheme.Type.HTTP)
            .description("로그인 후 받은 access token을 입력해주세요")
            .scheme(AUTH_SCHEME)
            .bearerFormat(BEARER_FORMAT));

    return new OpenAPI()
        .addSecurityItem(securityRequirement)
        .components(components);
  }
}
