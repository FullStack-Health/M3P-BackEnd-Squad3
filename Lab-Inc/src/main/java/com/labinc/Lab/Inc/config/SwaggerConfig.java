package com.labinc.Lab.Inc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Lab Inc.")
                        .version("1.0.0")
                        .description("A API Lab Inc é uma aplicação de backend construída com Spring Boot, " +
                                "desenhada para gerir pacientes, consultas e exames em ambiente hospitalar. " +
                                "Essa API fornece endpoints para criar, ler, atualizar e excluir (CRUD) " +
                                "informações relacionadas a pacientes, consultas e exames, bem como autenticação e autorização de usuários, " +
                                "implementados com o Spring Security."))
                .addSecurityItem(new SecurityRequirement().addList("SecurityScheme"))
                .components(new Components()
                        .addSecuritySchemes("SecurityScheme", new SecurityScheme()
                                .name("SecurityScheme")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

    }
}
