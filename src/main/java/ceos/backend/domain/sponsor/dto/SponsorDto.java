package ceos.backend.domain.sponsor.dto;


import ceos.backend.domain.sponsor.domain.Sponsor;
import ceos.backend.domain.sponsor.vo.SponsorVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SponsorDto {

    private Long id;
    private String name;
    private String imageUrl;

    @Builder
    private SponsorDto(Long id, String name, String imageUrl) {
        this.id = id;
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
                .id(sponsor.getId())
                .name(sponsor.getName())
                .imageUrl(sponsor.getImageUrl())
                .build();
    }
}
