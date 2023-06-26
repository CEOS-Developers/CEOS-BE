package ceos.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Value("${server.url}")
    private String SERVER_URL;

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("CEOS WEB API")
                .version(springdocVersion)
                .description("CEOS WEB API 입니다.");

        // JWT 설정
        String jwtSchemeName = "jwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
//                .servers(Arrays.asList(
//                        new Server().url(SERVER_URL)))
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    // 공개용
    @Bean
    public GroupedOpenApi api()
    {
        return GroupedOpenApi.builder()
                .group("ceos")
                .packagesToScan("ceos.backend")
                .pathsToMatch("/**")
                .build();
    }
}