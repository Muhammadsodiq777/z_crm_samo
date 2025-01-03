package com.zulfiqor.z_crm_zulfiqor.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
    name = "Authorization",
    bearerFormat = "jwt",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer"
)
@OpenAPIDefinition(
    info = @Info(title = "Zulfiqor Crm API"),
    security = @SecurityRequirement(name = "Authorization"),
    servers = {
        @Server(url = "https://zcrmsamo-production.up.railway.app", description = "Railway"),
        @Server(url = "http://localhost:8088", description = "Local Server")
    }
)
public class SwaggerConfig {
}
