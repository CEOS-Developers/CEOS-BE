package ceos.backend.domain.awards.domain;


import ceos.backend.domain.awards.dto.request.AwardsRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StartDate {

    @Id private int generation;

    @NotNull private String startDate;

    // 생성자
    @Builder
    public StartDate(int generation, String startDate) {
        this.generation = generation;
        this.startDate = startDate;
    }

    // 정적 팩토리 메서드
    public static StartDate from(AwardsRequest awardsRequest) {
        return StartDate.builder()
                .generation(awardsRequest.getGeneration())
                .startDate(awardsRequest.getStartDate())
                .build();
    }

    public void updateStartDate(String startDate) {
        this.startDate = startDate;
    }
}
