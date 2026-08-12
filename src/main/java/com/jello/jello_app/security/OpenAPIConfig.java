package com.jello.jello_app.security;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI orderServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Jello App API")
                        .description("Swagger for the Jello App")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Em caso de duvidas, pergunte aqui.")
                        .url("https://www.google.com"));
    }
}
