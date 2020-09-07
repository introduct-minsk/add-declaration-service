package com.ee.declaration.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(getComponents()).info(getInfo());
    }

    private Info getInfo() {
        return new Info().title("Add Declaration Service API").version("1.0");
    }

    private Components getComponents() {
        return new Components();
    }

}
