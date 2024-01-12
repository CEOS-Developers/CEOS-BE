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
    private String startupName;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String serviceUrl;

    @NotNull
    private Integer generation;

    @NotBlank
    private String founderName;

    @Builder
    public Startup(String startupName, String imageUrl, String serviceUrl, Integer generation, String founderName) {
        this.startupName = startupName;
        this.imageUrl = imageUrl;
        this.serviceUrl = serviceUrl;
        this.generation = generation;
        this.founderName = founderName;
    }

}
