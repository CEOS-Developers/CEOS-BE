package ceos.backend.domain.sponsor.dto.response;

import ceos.backend.domain.sponsor.dto.SponsorDto;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetAllSponsorsResponse {

    List<SponsorDto> sponsors;
    PageInfo pageInfo;

    @Builder
    private GetAllSponsorsResponse(List<SponsorDto> sponsors, PageInfo pageInfo) {
        this.sponsors = sponsors;
        this.pageInfo = pageInfo;
    }

    public static GetAllSponsorsResponse of(List<SponsorDto> sponsors, PageInfo pageInfo) {
        return GetAllSponsorsResponse.builder()
                .sponsors(sponsors)
                .pageInfo(pageInfo)
                .build();
    }
}
