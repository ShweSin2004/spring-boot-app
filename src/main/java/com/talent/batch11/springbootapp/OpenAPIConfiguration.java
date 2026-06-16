package com.talent.batch11.springbootapp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;


import java.util.Collections;
import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Value("${domain}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {

        Server server = new Server()
                .url(serverUrl)
                .description("ATM Development Server");

        Contact contact = new Contact()
                .name("Talent Program")
                .email("talentprogram@aceplussolutions.com")
                .url("https://aceplussolutions.com/");

        Info info = new Info()
                .title("ATM System API")
                .version("1.0.0")
                .contact(contact);

        SecurityScheme apiKeyScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("apiKey")
                .description("API Key for public endpoints");

        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Bearer token");

        SecurityRequirement globalSecurity = new SecurityRequirement()
                .addList("API_KEY")
                .addList("BEARER_JWT");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server))
                .components(new Components()
                        .addSecuritySchemes("API_KEY", apiKeyScheme)
                        .addSecuritySchemes("BEARER_JWT", bearerScheme))
                .addSecurityItem(globalSecurity);
    }


    @Bean
    public GlobalOpenApiCustomizer globalHeaderCustomizer() {
        return openApi -> openApi.getPaths().forEach((path, pathItem) ->
                pathItem.readOperations().forEach(operation -> {

                    List<Parameter> parameters = operation.getParameters();
                    if (parameters == null) {
                        parameters = Collections.emptyList();
                    }

                    operation.addParametersItem(new Parameter()
                            .in("header")
                            .name("apiKey")
                            .description("API Key for accessing the APIs")
                            .required(false));

                })
        );
    }
}
