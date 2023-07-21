package ceos.backend.domain.sponsor.mapper;


import ceos.backend.domain.sponsor.domain.Sponsor;
import ceos.backend.domain.sponsor.dto.SponsorDto;
import ceos.backend.domain.sponsor.dto.response.GetAllSponsorsResponse;
import ceos.backend.global.common.dto.PageInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SponsorMapper {
    public GetAllSponsorsResponse toManagementsPage(List<Sponsor> sponsors, PageInfo pageInfo) {
        List<SponsorDto> sponsorDtoList = new ArrayList<>();
        for (Sponsor sponsor : sponsors) {
            SponsorDto sponsorDto = SponsorDto.entityToDto(sponsor);
            sponsorDtoList.add(sponsorDto);
        }
        return GetAllSponsorsResponse.of(sponsorDtoList, pageInfo);
    }
}
