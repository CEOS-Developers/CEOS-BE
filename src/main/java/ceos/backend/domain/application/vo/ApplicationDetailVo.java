package ceos.backend.domain.application.vo;

import ceos.backend.domain.application.domain.ApplicationDetail;
import ceos.backend.domain.application.domain.Application;
import ceos.backend.global.common.annotation.DateFormat;
import ceos.backend.global.common.annotation.ValidEnum;
import ceos.backend.global.common.entity.Part;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationDetailVo {
    @Schema(defaultValue = "99999999", description = "지원 기수")
    @NotNull(message = "지원 기수를 입력해주세요")
    @Positive
    private int generation;

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

    @Schema(defaultValue = "백엔드", description = "지원자 파트")
    @ValidEnum(target = Part.class)
    private Part part;

    @Builder
    private ApplicationDetailVo(int generation, LocalDate otDate, LocalDate demodayDate,
                               String otherActivities, Part part) {
        this.generation = generation;
        this.otDate = otDate;
        this.demodayDate = demodayDate;
        this.otherActivities = otherActivities;
        this.part = part;
    }

    public static ApplicationDetailVo from(ApplicationDetail applicationDetail) {
        return ApplicationDetailVo.builder()
                .generation(applicationDetail.getGeneration())
                .otDate(applicationDetail.getOtDate())
                .demodayDate(applicationDetail.getDemodayDate())
                .otherActivities(applicationDetail.getOtherActivities())
                .part(applicationDetail.getPart())
                .build();
    }

    public static ApplicationDetailVo from(Application application) {
        return ApplicationDetailVo.builder()
                .generation(application.getApplicationDetail().getGeneration())
                .otDate(application.getApplicationDetail().getOtDate())
                .demodayDate(application.getApplicationDetail().getDemodayDate())
                .otherActivities(application.getApplicationDetail().getOtherActivities())
                .part(application.getApplicationDetail().getPart())
                .build();
    }
}
