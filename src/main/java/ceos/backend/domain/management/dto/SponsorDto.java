package ceos.backend.domain.management.dto;

import ceos.backend.domain.sponsor.domain.Sponsor;
import ceos.backend.domain.sponsor.vo.SponsorVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SponsorDto {

    private String name;
    private String imageUrl;

    @Builder
    private SponsorDto(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static SponsorDto voToDto(SponsorVo sponsorVo) {
        return SponsorDto.builder()
                .name(sponsorVo.getName())
                .imageUrl(sponsorVo.getImageUrl())
                .build();
    }

    public static SponsorDto entityToDto(Sponsor sponsor) {
        return SponsorDto.builder()
                .name(sponsor.getName())
                .imageUrl(sponsor.getImageUrl())
                .build();
    }
}
