package ceos.backend.domain.application.domain;


import ceos.backend.domain.application.vo.ApplicantInfoVo;
import ceos.backend.global.common.entity.University;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantInfo {

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull private LocalDate birth;

    @NotNull
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max = 11)
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

    @NotNull @PositiveOrZero private int semestersLeftNumber;

    @Builder
    private ApplicantInfo(
            String name,
            Gender gender,
            LocalDate birth,
            String email,
            String phoneNumber,
            University university,
            String major,
            String uuid,
            int semestersLeftNumber) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.major = major;
        this.uuid = uuid;
        this.semestersLeftNumber = semestersLeftNumber;
    }

    public static ApplicantInfo of(ApplicantInfoVo applicantInfoVo, String uuid) {
        return ApplicantInfo.builder()
                .name(applicantInfoVo.getName())
                .gender(applicantInfoVo.getGender())
                .birth(applicantInfoVo.getBirth())
                .email(applicantInfoVo.getEmail())
                .phoneNumber(applicantInfoVo.getPhoneNumber())
                .university(applicantInfoVo.getUniversity())
                .major(applicantInfoVo.getMajor())
                .uuid(uuid)
                .semestersLeftNumber(applicantInfoVo.getSemestersLeftNumber())
                .build();
    }
}
