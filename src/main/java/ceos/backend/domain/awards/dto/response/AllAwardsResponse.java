package ceos.backend.domain.awards.dto.response;


import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AllAwardsResponse {
    private List<GenerationAwardsResponse> content;
    PageInfo pageInfo;

    @Builder
    private AllAwardsResponse(List<GenerationAwardsResponse> generationAwards, PageInfo pageInfo) {
        this.content = generationAwards;
        this.pageInfo = pageInfo;
    }

    public static AllAwardsResponse of(
            List<GenerationAwardsResponse> generationAwards, PageInfo pageInfo) {
        return AllAwardsResponse.builder()
                .generationAwards(generationAwards)
                .pageInfo(pageInfo)
                .build();
    }
}
