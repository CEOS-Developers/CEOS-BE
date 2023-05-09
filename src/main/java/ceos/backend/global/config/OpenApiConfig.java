package ceos.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("CEOS WEB API")
                .version(springdocVersion)
                .description("CEOS WEB API 입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

    // 공개용
    @Bean
    public GroupedOpenApi api()
    {
        return GroupedOpenApi.builder()
                .group("ceos")
                .packagesToScan("ceos.backend")
                .pathsToMatch("/api/**")
                .build();
    }

    // 확인용
    // TODO: 확인용 group 삭제
    @Bean
    public GroupedOpenApi test()
    {
        return GroupedOpenApi.builder()
                .group("test")
                .packagesToScan("ceos.backend")
                .pathsToMatch("/example/**")
                .build();
    }
}