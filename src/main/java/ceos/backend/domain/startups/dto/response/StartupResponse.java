package ceos.backend.domain.startups.dto.response;

import ceos.backend.domain.startups.domain.Startup;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StartupResponse {

    private Long startupId;

    private String serviceName;

    private String companyName;

    private String imageUrl;

    private String serviceUrl;

    private Integer generation;

    private String founder;

    @Builder
    public StartupResponse(Long startupId, String serviceName, String companyName, String imageUrl, String serviceUrl, Integer generation, String founder) {
        this.startupId = startupId;
        this.serviceName = serviceName;
        this.companyName = companyName;
        this.imageUrl = imageUrl;
        this.serviceUrl = serviceUrl;
        this.generation = generation;
        this.founder = founder;
    }

    public static StartupResponse fromEntity(Startup startup) {
        return StartupResponse.builder()
                .startupId(startup.getId())
                .serviceName(startup.getServiceName())
                .companyName(startup.getCompanyName())
                .imageUrl(startup.getImageUrl())
                .serviceUrl(startup.getServiceUrl())
                .generation(startup.getGeneration())
                .founder(startup.getFounder())
                .build();
    }

}
