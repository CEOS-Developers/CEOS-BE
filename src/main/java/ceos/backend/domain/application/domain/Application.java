package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.BaseEntity;
import ceos.backend.global.common.entity.Part;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Application extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Embedded
    private ApplicantInfo applicantInfo;

    @NotNull
    private int generation;

    @NotNull
    @Size(max = 10)
    @Enumerated(EnumType.STRING)
    private Part part;

    @NotNull
    private int semestersLeftNumber;

    @Size(max = 100)
    private String otherActivities;

    @NotNull
    private boolean otCheck;

    @NotNull
    private boolean demodayCheck;

    private LocalDateTime interviewDatetime;

    @NotNull
    @ColumnDefault("false")
    private boolean interviewCheck;

    @NotNull
    @ColumnDefault("false")
    private boolean documentPass;

    @NotNull
    @ColumnDefault("false")
    private boolean finalPass;

    @NotNull
    @ColumnDefault("false")
    private boolean finalCheck;


    // 생성자
    @Builder
    private Application(ApplicantInfo applicantInfo,
                        int generation,
                        Part part,
                        int semestersLeftNumber,
                        String otherActivities,
                        boolean otCheck,
                        boolean demodayCheck,
                        LocalDateTime interviewDatetime,
                        boolean interviewCheck,
                        boolean documentPass,
                        boolean finalPass,
                        boolean finalCheck) {

        this.applicantInfo = applicantInfo;
        this.generation = generation;
        this.part = part;
        this.semestersLeftNumber = semestersLeftNumber;
        this.otherActivities = otherActivities;
        this.otCheck = otCheck;
        this.demodayCheck = demodayCheck;
        this.interviewDatetime = interviewDatetime;
        this.interviewCheck = interviewCheck;
        this.documentPass = documentPass;
        this.finalPass = finalPass;
        this.finalCheck = finalCheck;
    }

    // 정적 팩토리 메서드
}
