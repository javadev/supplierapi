package com.cs.roomdbapi.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfiguration {

    @Value("${project.version}")
    String version;

    String title = "Supplier API.";

    String description = "Provides an API endpoints to access data on RoomDB. <br/>" +
            "More detailed information can be found in project " +
            "<a href=\"https://repo.cultuzz.com/roomdb/supplierapi/-/blob/master/README.md\">README file</a> <br/>";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                );
    }
}
