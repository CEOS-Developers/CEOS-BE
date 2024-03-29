package ceos.backend.domain.sponsor.dto.response;


import ceos.backend.domain.sponsor.dto.SponsorDto;
import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllSponsorsResponse {

    List<SponsorDto> content;
    PageInfo pageInfo;

    @Builder
    private GetAllSponsorsResponse(List<SponsorDto> sponsors, PageInfo pageInfo) {
        this.content = sponsors;
        this.pageInfo = pageInfo;
    }

    public static GetAllSponsorsResponse of(List<SponsorDto> sponsors, PageInfo pageInfo) {
        return GetAllSponsorsResponse.builder().sponsors(sponsors).pageInfo(pageInfo).build();
    }
}
