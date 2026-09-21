package com.notifysys.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI notificationSystemOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Notification System API")
                .version("v1")
                .description("Create and fetch notifications across email, SMS and push channels."));
    }
}
