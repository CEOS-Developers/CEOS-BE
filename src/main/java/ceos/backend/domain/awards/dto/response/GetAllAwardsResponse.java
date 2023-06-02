package ceos.backend.domain.awards.dto.response;

import ceos.backend.domain.awards.dto.AwardsDTO;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetAllAwardsResponse {
    private List<AwardsDTO> awards;
    PageInfo pageInfo;

    @Builder
    private GetAllAwardsResponse(List<AwardsDTO> awards, PageInfo pageInfo) {
        this.awards = awards;
        this.pageInfo = pageInfo;
    }

    public static GetAllAwardsResponse of(List<AwardsDTO> awards, PageInfo pageInfo) {
        return GetAllAwardsResponse.builder()
                .awards(awards)
                .pageInfo(pageInfo)
                .build();
    }
}
