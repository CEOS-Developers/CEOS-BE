package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.vo.ProjectInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AllAwardsResponse {
    private List<GenerationAwardsResponse> generationAwards;
    PageInfo pageInfo;

    @Builder
    private AllAwardsResponse(List<GenerationAwardsResponse> generationAwards, PageInfo pageInfo) {
        this.generationAwards = generationAwards;
        this.pageInfo = pageInfo;
    }

    public static AllAwardsResponse of(List<GenerationAwardsResponse> generationAwards, PageInfo pageInfo) {
        return AllAwardsResponse.builder()
                .generationAwards(generationAwards)
                .pageInfo(pageInfo)
                .build();
    }
}
