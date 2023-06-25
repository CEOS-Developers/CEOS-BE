package ceos.backend.domain.sponsor.mapper;

import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.dto.ManagementDto;
import ceos.backend.domain.management.dto.SponsorDto;
import ceos.backend.domain.management.dto.response.GetAllManagementsResponse;
import ceos.backend.domain.sponsor.domain.Sponsor;
import ceos.backend.domain.sponsor.dto.response.GetAllSponsorsResponse;
import ceos.backend.global.common.dto.PageInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
