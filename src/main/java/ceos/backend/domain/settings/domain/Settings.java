package ceos.backend.domain.settings.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Settings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id")
    private Long id;

    @NotNull
    private int generation;

    @NotNull
    private String prodImg;

    @NotNull
    private String designImg;

    @NotNull
    private String devImg;

    @NotNull
    private String prodStudyUrl;

    @NotNull
    private String designStudyUrl;

    @NotNull
    private String devStudyUrl;

    @NotNull
    private LocalDate startDateDoc;

    @NotNull
    private LocalDate endDateDoc;

    @NotNull
    private LocalDate resultDateDoc;

    @NotNull
    private LocalDate startDateInterview;

    @NotNull
    private LocalDate endDateInterview;

    @NotNull
    private LocalDate resultDateFinal;

    @NotNull
    private LocalDate openChatUrl;

    @NotNull
    private LocalDate otDate;

    @NotNull
    private LocalDate demodayDate;
}
