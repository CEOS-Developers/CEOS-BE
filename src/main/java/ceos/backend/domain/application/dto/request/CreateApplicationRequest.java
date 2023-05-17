package ceos.backend.domain.application.dto.request;

import ceos.backend.domain.application.domain.Gender;
import ceos.backend.domain.application.vo.AnswerVo;
import ceos.backend.global.common.annotation.*;
import ceos.backend.global.common.entity.Part;
import ceos.backend.global.common.entity.University;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateApplicationRequest {
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

    @Schema(type = "string",
            pattern = "yyyy.MM.dd",
            defaultValue = "2023.03.20",
            description = "ot 날짜")
    @NotNull(message = "ot 날짜를 입력해주세요")
    @DateFormat
    private LocalDate otDate;

    @Schema(type = "string",
            pattern = "yyyy.MM.dd",
            defaultValue = "2023.03.20",
            description = "데모데이 날짜")
    @NotNull(message = "데모데이 날짜를 입력해주세요")
    @DateFormat
    private LocalDate demodayDate;

    @Schema(defaultValue = "구르기", description = "지원자 다른 활동")
    @NotEmpty(message = "지원자 다른 활동을 입력해주세요")
    private String otherActivities;

    @Valid
    private List<AnswerVo> commonAnswers;

    @Schema(defaultValue = "백엔드", description = "지원자 파트")
    @ValidEnum(target = Part.class)
    private Part part;

    @Valid
    private List<AnswerVo> partAnswers;

    @Schema(type = "string",
            description = "불가능 시간 선택 \"yyyy.MM.dd HH:mm:ss\" 형식으로 적어주세요!")
    @DateTimeFormat
    private List<LocalDateTime> unableTimes;
}
