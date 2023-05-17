package ceos.backend.domain.application.domain;

import ceos.backend.domain.application.vo.ApplicationDetailVo;
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

import java.time.LocalDate;

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
    private LocalDate otDate;

    @NotNull
    private LocalDate demodayDate;

    @Builder
    private ApplicationDetail(int generation, Part part, int semestersLeftNumber, String otherActivities,
                             LocalDate otDate, LocalDate demodayDate) {
        this.generation = generation;
        this.part = part;
        this.semestersLeftNumber = semestersLeftNumber;
        this.otherActivities = otherActivities;
        this.otDate = otDate;
        this.demodayDate = demodayDate;
    }

    public static ApplicationDetail of(ApplicationDetailVo applicationDetailVo) {
        return ApplicationDetail.builder()
                .generation(applicationDetailVo.getGeneration())
                .part(applicationDetailVo.getPart())
                .semestersLeftNumber(applicationDetailVo.getSemestersLeftNumber())
                .otherActivities(applicationDetailVo.getOtherActivities())
                .otDate(applicationDetailVo.getOtDate())
                .demodayDate(applicationDetailVo.getDemodayDate())
                .build();
    }
}
