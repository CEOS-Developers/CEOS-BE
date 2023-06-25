package ceos.backend.domain.awards.dto.response;

import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AllAwardsResponse {
    private List<AwardsResponse> awards;
    PageInfo pageInfo;

    @Builder
    private AllAwardsResponse(List<AwardsResponse> awards, PageInfo pageInfo) {
        this.awards = awards;
        this.pageInfo = pageInfo;
    }

    public static AllAwardsResponse of(List<AwardsResponse> awards, PageInfo pageInfo) {
        return AllAwardsResponse.builder()
                .awards(awards)
                .pageInfo(pageInfo)
                .build();
    }
}
