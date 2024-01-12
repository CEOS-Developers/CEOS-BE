package ceos.backend.domain.startups.dto.response;

import ceos.backend.domain.startups.domain.Startup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.sql.ast.tree.expression.Star;

@Getter
public class StartupsElement {
    private Long startupId;
    private String serviceName;
    private Integer generation;
    private String founder;

    @Builder
    public StartupsElement(Long startupId, String serviceName, Integer generation, String founder) {
        this.startupId = startupId;
        this.serviceName = serviceName;
        this.generation = generation;
        this.founder = founder;
    }

    public static StartupsElement fromEntity(Startup startup) {
        return StartupsElement.builder()
                .startupId(startup.getId())
                .serviceName(startup.getServiceName())
                .generation(startup.getGeneration())
                .founder(startup.getFounder())
                .build();
    }

}
