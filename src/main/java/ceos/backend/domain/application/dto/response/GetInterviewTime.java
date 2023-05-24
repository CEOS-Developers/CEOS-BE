package ceos.backend.domain.application.dto.response;

import ceos.backend.domain.application.vo.InterviewTimeVo;
import ceos.backend.global.common.annotation.ValidDateList;
import ceos.backend.global.common.annotation.ValidTimeDuration;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetInterviewTime {
    private List<InterviewTimeVo> times;

    @Builder
    private GetInterviewTime(List<InterviewTimeVo> times) {
        this.times = times;
    }

    public static GetInterviewTime from(List<InterviewTimeVo> times) {
        return GetInterviewTime.builder()
                .times(times)
                .build();
    }
}
