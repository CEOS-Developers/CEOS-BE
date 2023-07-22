package ceos.backend.domain.application.dto.response;


import ceos.backend.domain.application.vo.InterviewTimeVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetInterviewTime {
    private List<InterviewTimeVo> times;

    @Builder
    private GetInterviewTime(List<InterviewTimeVo> times) {
        this.times = times;
    }

    public static GetInterviewTime from(List<InterviewTimeVo> times) {
        return GetInterviewTime.builder().times(times).build();
    }
}
