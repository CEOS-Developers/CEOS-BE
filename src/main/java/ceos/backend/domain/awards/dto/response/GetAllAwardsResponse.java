package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.dto.AwardsDto;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetAllAwardsResponse {
    private List<AwardsDto> awards;
    PageInfo pageInfo;

    @Builder
    private GetAllAwardsResponse(List<AwardsDto> awards, PageInfo pageInfo) {
        this.awards = awards;
        this.pageInfo = pageInfo;
    }

    public static GetAllAwardsResponse of(List<AwardsDto> awards, PageInfo pageInfo) {
        return GetAllAwardsResponse.builder()
                .awards(awards)
                .pageInfo(pageInfo)
                .build();
    }
}
