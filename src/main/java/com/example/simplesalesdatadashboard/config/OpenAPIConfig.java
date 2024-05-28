package com.example.simplesalesdatadashboard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Sales Dashboard API")
                .version("1.0.0")
                .description("API for managing sales data"))
            .addServersItem(new Server().url("http://localhost:8080/api"))
            .tags(List.of(
                new Tag().name("Sales Data").description("Operations related to sales data")
            ));
    }
}

