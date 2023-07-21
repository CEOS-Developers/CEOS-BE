package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.domain.Awards;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AwardsResponse {

    private String content;
    private LocalDate startDate;

    @Builder
    private AwardsResponse(String content,
                           LocalDate startDate)
    {
        this.content = content;
        this.startDate = startDate;
    }

    public static AwardsResponse to(Awards awards){
        return AwardsResponse.builder()
                .content(awards.getContent())
                .startDate(awards.getStartDate())
                .build();
    }
}
