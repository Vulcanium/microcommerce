package com.vulcanium.ecommerce.microcommerce.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class DocumentationConfig {

    @Bean
    public OpenAPI docInformation() {
        OpenAPI openAPI = new OpenAPI();

        openAPI.info(
                new Info()
                        .title("Microcommerce API")
                        .version("1.0")
                        .description("Microcommerce Microservice API Documentation")
        );

        return openAPI;
    }

    @Bean
    public GroupedOpenApi docFilter() {
        return GroupedOpenApi.builder()
                .group("microcommerce")
                .pathsToExclude("/**/test/**") // To exclude URLs containing /test
                .build();
    }
}
