package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamicInsert
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Application extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    private char gender; // enum

    @NotNull
    private LocalDate birth;

    @NotNull
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 11)
    @Column(unique = true)
    private String phoneNumber;

    @NotNull
    @Size(max = 10)
    private String university; // enum

    @NotNull
    @Size(max = 20)
    private String major;

    @NotNull
    private int generation;

    @NotNull
    @Size(max = 10)
    private String part; // enum

    @NotNull
    private int semestersLeftNumber;

    @Size(max = 100)
    private String otherActivities;

    @Size(max = 100)
    @Column(unique = true)
    private String uuid;

    @NotNull
    @ColumnDefault("false")
    private boolean otCheck;

    @NotNull
    @ColumnDefault("false")
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


// TODO : 생성자 UUID 만들기
// @Builder
}
