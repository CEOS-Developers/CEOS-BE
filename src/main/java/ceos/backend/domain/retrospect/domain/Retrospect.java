package ceos.backend.domain.retrospect.domain;


import ceos.backend.domain.retrospect.dto.request.CreateRetrospectRequest;
import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.Part;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Retrospect extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "retrospect_id")
    private Long id;

    @NotBlank private String title;

    @NotBlank private String url;

    @NotBlank private String writer;

    @NotNull @Positive private Integer generation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Part part;

    @Builder
    public Retrospect(String title, String url, String writer, Integer generation, Part part) {
        this.title = title;
        this.url = url;
        this.writer = writer;
        this.generation = generation;
        this.part = part;
    }

    public void update(CreateRetrospectRequest createRetrospectRequest) {
        title = createRetrospectRequest.getTitle();
        url = createRetrospectRequest.getUrl();
        writer = createRetrospectRequest.getWriter();
        generation = createRetrospectRequest.getGeneration();
        part = createRetrospectRequest.getPart();
    }
}
