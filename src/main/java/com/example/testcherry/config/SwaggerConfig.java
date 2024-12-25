package com.example.testcherry.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "API DOCS",
        description = "API 설명입니다",
        version = "1.0"
    ),
    servers = @Server(
        url = "https://relaxed-daveta-kiraz-787c046b.koyeb.app",
        description = "Koyeb 으로 배포한 서비스입니다."
    )
)

@Configuration
public class SwaggerConfig {

//  @Bean
//  public OpenAPI openAPI() {
//    String JWT = "JWT";
//    SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT);
//
//    Components components = new Components()
//        .addSecuritySchemes(JWT, new SecurityScheme()
//            .name(JWT)
//            .type(SecurityScheme.Type.HTTP)
//            .scheme("bearer")
//            .bearerFormat(JWT));
//
//    return new OpenAPI()
//        .addSecurityItem(securityRequirement)
//        .components(components);
//  }
}
