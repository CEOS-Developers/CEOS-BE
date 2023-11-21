package ceos.backend.domain.application.vo;


import ceos.backend.domain.application.domain.ApplicantInfo;
import ceos.backend.domain.application.domain.Application;
import ceos.backend.domain.application.domain.Gender;
import ceos.backend.global.common.annotation.DateFormat;
import ceos.backend.global.common.annotation.ValidEmail;
import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.annotation.ValidPhone;
import ceos.backend.global.common.entity.University;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicantInfoVo {
    @Schema(defaultValue = "김영한", description = "지원자 이름")
    @NotEmpty(message = "지원자 이름을 입력해주세요")
    private String name;

    @Schema(defaultValue = "남성", description = "지원자 이름")
    @ValidEnum(target = Gender.class)
    private Gender gender;

    @Schema(
            type = "string",
            pattern = "yyyy.MM.dd",
            defaultValue = "2023.03.20",
            description = "지원자 생년월일")
    @NotNull(message = "지원자 생년월일을 입력해주세요")
    @DateFormat
    private LocalDate birth;

    @Schema(defaultValue = "ceos@ceos-sinchon.com", description = "지원자 이메일")
    @ValidEmail
    private String email;

    @Schema(defaultValue = "010-1234-5678", description = "지원자 전화번호")
    @ValidPhone
    private String phoneNumber;

    @Schema(defaultValue = "홍익대학교", description = "지원자 대학교")
    @ValidEnum(target = University.class)
    private University university;

    @Schema(defaultValue = "컴퓨터 공학과", description = "지원자 전공")
    @NotEmpty(message = "지원자 전공을 입력해주세요")
    private String major;

    @Schema(defaultValue = "99999999", description = "지원자 남은 학기 수")
    @NotNull(message = "지원자 남은 학기 수를 입력해주세요")
    @PositiveOrZero
    private int semestersLeftNumber;

    @Builder
    private ApplicantInfoVo(
            String name,
            Gender gender,
            LocalDate birth,
            String email,
            String phoneNumber,
            University university,
            String major,
            int semestersLeftNumber) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.major = major;
        this.semestersLeftNumber = semestersLeftNumber;
    }

    public static ApplicantInfoVo from(ApplicantInfo applicantInfo) {
        return ApplicantInfoVo.builder()
                .name(applicantInfo.getName())
                .gender(applicantInfo.getGender())
                .birth(applicantInfo.getBirth())
                .email(applicantInfo.getEmail())
                .phoneNumber(applicantInfo.getPhoneNumber())
                .university(applicantInfo.getUniversity())
                .major(applicantInfo.getMajor())
                .semestersLeftNumber(applicantInfo.getSemestersLeftNumber())
                .build();
    }

    public static ApplicantInfoVo from(Application application) {
        return ApplicantInfoVo.builder()
                .name(application.getApplicantInfo().getName())
                .gender(application.getApplicantInfo().getGender())
                .birth(application.getApplicantInfo().getBirth())
                .email(application.getApplicantInfo().getEmail())
                .phoneNumber(application.getApplicantInfo().getPhoneNumber())
                .university(application.getApplicantInfo().getUniversity())
                .major(application.getApplicantInfo().getMajor())
                .semestersLeftNumber(application.getApplicantInfo().getSemestersLeftNumber())
                .build();
    }
}
