package ceos.backend.domain.startups.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "startup_id")
    private Long id;

    @NotBlank
    private String serviceName;

    private String companyName;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String serviceUrl;

    @NotNull
    private Integer generation;

    @NotBlank
    private String founder;

    @Builder
    public Startup(String serviceName, String companyName, String imageUrl, String serviceUrl, Integer generation, String founder) {
        this.serviceName = serviceName;
        this.companyName = companyName;
        this.imageUrl = imageUrl;
        this.serviceUrl = serviceUrl;
        this.generation = generation;
        this.founder = founder;
    }

}
