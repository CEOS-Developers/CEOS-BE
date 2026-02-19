package ceos.backend.domain.startup.dto.request;


import ceos.backend.domain.startup.domain.Startup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class StartupRequest {

    @NotBlank
    @Schema(defaultValue = "Repick", description = "서비스명")
    private String serviceName;

    @Schema(defaultValue = "(주)Repick", description = "회사명(생략가능)")
    private String companyName;

    @NotBlank
    @Schema(description = "서비스 이미지 url")
    private String imageUrl;

    @NotBlank
    @Schema(description = "서비스 url")
    private String serviceUrl;

    @NotNull
    @Schema(defaultValue = "17", description = "창업자 활동 기수")
    private Integer generation;

    @NotBlank
    @Schema(defaultValue = "이도현", description = "창업자 이름")
    private String founder;

    public Startup toEntity() {
        return Startup.builder()
                .serviceName(serviceName)
                .companyName(companyName)
                .imageUrl(imageUrl)
                .serviceUrl(serviceUrl)
                .generation(generation)
                .founder(founder)
                .build();
    }
}
