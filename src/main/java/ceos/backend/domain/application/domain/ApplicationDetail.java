package ceos.backend.domain.application.domain;


import ceos.backend.domain.application.dto.request.CreateApplicationRequest;
import ceos.backend.global.common.entity.Part;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationDetail {
    @NotNull @Positive private int generation;

    @NotNull
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private Part part;

    @Size(max = 100)
    @Column(columnDefinition = "TEXT")
    private String otherActivities;

    @NotNull private LocalDate otDate;

    @NotNull private LocalDate demodayDate;

    @Builder
    private ApplicationDetail(
            int generation,
            Part part,
            String otherActivities,
            LocalDate otDate,
            LocalDate demodayDate) {
        this.generation = generation;
        this.part = part;
        this.otherActivities = otherActivities;
        this.otDate = otDate;
        this.demodayDate = demodayDate;
    }

    public static ApplicationDetail of(CreateApplicationRequest request, int generation) {
        return ApplicationDetail.builder()
                .generation(generation)
                .part(request.getPart())
                .otherActivities(request.getOtherActivities())
                .otDate(request.getOtDate())
                .demodayDate(request.getDemodayDate())
                .build();
    }
}
