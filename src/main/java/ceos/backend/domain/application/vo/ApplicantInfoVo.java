package ceos.backend.domain.application.vo;

import ceos.backend.domain.application.domain.Gender;
import ceos.backend.global.common.annotation.DateFormat;
import ceos.backend.global.common.annotation.ValidEmail;
import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.annotation.ValidPhone;
import ceos.backend.global.common.entity.University;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ApplicantInfoVo {
    @Schema(defaultValue = "김영한", description = "지원자 이름")
    @NotEmpty(message = "지원자 이름을 입력해주세요")
    private String name;

    @Schema(defaultValue = "남성", description = "지원자 이름")
    @ValidEnum(target = Gender.class)
    private Gender gender;

    @Schema(type = "string",
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
    @Positive
    private int semestersLeftNumber;
}