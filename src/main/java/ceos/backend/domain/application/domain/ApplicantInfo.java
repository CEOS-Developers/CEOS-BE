package ceos.backend.domain.application.domain;

import ceos.backend.global.common.entity.University;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;


import java.time.LocalDate;
@Getter
@Embeddable
public class ApplicantInfo {

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

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
    @Enumerated(EnumType.STRING)
    private University university;

    @NotNull
    @Size(max = 20)
    private String major;

    @Size(max = 100)
    @Column(unique = true)
    private String uuid;

    // 생성자
    protected ApplicantInfo() {

    }

    public ApplicantInfo(String name,
                         Gender gender,
                         LocalDate birth,
                         String email,
                         String phoneNumber,
                         University university,
                         String major,
                         String uuid) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.major = major;
        this.uuid = uuid;
    }
}
