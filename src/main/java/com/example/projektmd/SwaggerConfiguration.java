package com.example.projektmd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ProjektMD api test")
                        .version("1.0")
                        .description("Narzędzie Swagger w projekcie"));
    }
}

// Konfiguracja Swagger ustawiająca tytuł, wersje oraz opis