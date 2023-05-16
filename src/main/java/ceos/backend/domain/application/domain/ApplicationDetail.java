package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.Part;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationDetail {
    @NotNull
    @Positive
    private int generation;

    @NotNull
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private Part part;

    @NotNull
    @Positive
    private int semestersLeftNumber;

    @Size(max = 100)
    @Column(columnDefinition = "TEXT")
    private String otherActivities;

    @NotNull
    private LocalDateTime otDate;

    @NotNull
    private LocalDateTime demodayDate;

    @Builder
    private ApplicationDetail(int generation, Part part, int semestersLeftNumber, String otherActivities,
                             LocalDateTime otDate, LocalDateTime demodayDate) {
        this.generation = generation;
        this.part = part;
        this.semestersLeftNumber = semestersLeftNumber;
        this.otherActivities = otherActivities;
        this.otDate = otDate;
        this.demodayDate = demodayDate;
    }

    public static ApplicationDetail of(int generation, Part part, int semestersLeftNumber,
                                       String otherActivities, LocalDateTime otDate,
                                       LocalDateTime demodayDate) {
        return ApplicationDetail.builder()
                .generation(generation)
                .part(part)
                .semestersLeftNumber(semestersLeftNumber)
                .otherActivities(otherActivities)
                .otDate(otDate)
                .demodayDate(demodayDate)
                .build();
    }
}
