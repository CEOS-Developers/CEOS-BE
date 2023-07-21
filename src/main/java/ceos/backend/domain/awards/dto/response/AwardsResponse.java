package ceos.backend.domain.awards.dto.response;


import ceos.backend.domain.awards.domain.Awards;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AwardsResponse {

    private Long id;
    private String content;
    private LocalDate startDate;

    @Builder
    private AwardsResponse(Long id, String content, LocalDate startDate) {
        this.id = id;
        this.content = content;
        this.startDate = startDate;
    }

    public static AwardsResponse to(Awards awards) {
        return AwardsResponse.builder()
                .id(awards.getId())
                .content(awards.getContent())
                .startDate(awards.getStartDate())
                .build();
    }
}
