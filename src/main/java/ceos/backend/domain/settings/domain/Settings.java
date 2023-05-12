package ceos.backend.domain.settings.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String openChatUrl;

    @NotNull
    private LocalDate otDate;

    @NotNull
    private LocalDate demodayDate;

    // private LocalDate hackatonDate;

    // 생성자
    @Builder
    private Settings(int generation,
                     String prodImg,
                     String designImg,
                     String devImg,
                     String prodStudyUrl,
                     String designStudyUrl,
                     String devStudyUrl,
                     LocalDate startDateDoc,
                     LocalDate endDateDoc,
                     LocalDate resultDateDoc,
                     LocalDate startDateInterview,
                     LocalDate endDateInterview,
                     LocalDate resultDateFinal,
                     String openChatUrl,
                     LocalDate otDate,
                     LocalDate demodayDate) {
        this.generation = generation;
        this.prodImg = prodImg;
        this.designImg = designImg;
        this.devImg = devImg;
        this.prodStudyUrl = prodStudyUrl;
        this.designStudyUrl = designStudyUrl;
        this.devStudyUrl = devStudyUrl;
        this.startDateDoc = startDateDoc;
        this.endDateDoc = endDateDoc;
        this.resultDateDoc = resultDateDoc;
        this.startDateInterview = startDateInterview;
        this.endDateInterview = endDateInterview;
        this.resultDateFinal = resultDateFinal;
        this.openChatUrl = openChatUrl;
        this.otDate = otDate;
        this.demodayDate = demodayDate;
    }

    // 정적 팩토리 메서드
}
