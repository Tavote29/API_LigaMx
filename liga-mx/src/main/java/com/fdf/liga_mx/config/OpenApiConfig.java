package com.fdf.liga_mx.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("API Liga MX - Sistema de Gestión")
                        .version("v1.0.0")
                        .description("""
                            ## API para la gestión completa de la Liga MX
                            ### Descripción General
                            Sistema API para la gestión integral de la Liga MX. Proporciona operaciones CRUD para:
                            - Clubes de fútbol
                            - Jugadores
                            - Directores técnicos (DTs)
                            - Árbitros
                            - Estadios
                            - Catálogos del sistema
                            - Partidos
                            - Acontecimientos

                            ### Características principales:
                            - **CRUD completo** para todas las entidades principales
                            - **Búsquedas avanzadas** con filtros y paginación
                            - **Validación de datos** con mensajes descriptivos
                            - **Documentación completa** con Swagger/OpenAPI
                            - **Gestión de relaciones** entre entidades

                            ### Tecnologías utilizadas:
                            - Java 17
                            - Spring Boot 4
                            - Spring Data JPA
                            - Microsoft SQL Server
                            - Spring Security
                            - Springdoc OpenAPI 3.0

                            ### Endpoints principales:
                            - `/club` - Gestión de clubes
                            - `/jugador` - Gestión de jugadores
                            - `/dt` - Gestión de directores técnicos
                            - `/arbitro` - Gestión de árbitros
                            - `/estadio` - Gestión de estadios
                            - `/catalogos` - Consulta de catálogos del sistema
                            - `/partidos` - Gestión de partidos del sistema
                            - `/estadisticas` - Consulta de estadisticas del sistema
                            - `/acontecimientos` - Gestion de acontecimientos del sistema

                            ### Contacto y soporte:
                            Para reportar problemas o sugerencias, contactar al equipo de desarrollo.
                            """)
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Team LigaMX API")
                                .email("soporte@ligamx.com")
                                .url("https://ligamx.com/api")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local"),
                        new Server()
                                .url("https://api.ligamx.com")
                                .description("Servidor de producción")
                ))
                .tags(List.of(
                        new io.swagger.v3.oas.models.tags.Tag().name(SwaggerTags.CLUB_TAG).description(SwaggerTags.CLUB_DESC),
                        new io.swagger.v3.oas.models.tags.Tag().name(SwaggerTags.JUGADOR_TAG).description(SwaggerTags.JUGADOR_DESC),
                        new io.swagger.v3.oas.models.tags.Tag().name(SwaggerTags.DT_TAG).description(SwaggerTags.DT_DESC),
                        new io.swagger.v3.oas.models.tags.Tag().name(SwaggerTags.ARBITRO_TAG).description(SwaggerTags.ARBITRO_DESC),
                        new io.swagger.v3.oas.models.tags.Tag().name(SwaggerTags.ESTADIO_TAG).description(SwaggerTags.ESTADIO_DESC),
                        new io.swagger.v3.oas.models.tags.Tag().name(SwaggerTags.CATALOGOS_TAG).description(SwaggerTags.CATALOGOS_DESC)
                ))

                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}
