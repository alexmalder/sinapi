package com.vnmntn.sinapi.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://api.vnmntn.com"))
                .addServersItem(new Server().url("http://localhost:8080"))
                .info(new Info()
                        .title("Your API Title")
                        .description("Your API Description")
                        .version("1.0")
                );
    }
}