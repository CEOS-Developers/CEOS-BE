package ceos.backend.domain.sponsor.service;

import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.dto.SponsorDto;
import ceos.backend.domain.management.dto.response.GetAllManagementsResponse;
import ceos.backend.domain.sponsor.domain.Sponsor;
import ceos.backend.domain.sponsor.dto.response.GetAllSponsorsResponse;
import ceos.backend.domain.sponsor.exception.SponsorNotFound;
import ceos.backend.domain.sponsor.mapper.SponsorMapper;
import ceos.backend.domain.sponsor.repository.SponsorRepository;
import ceos.backend.domain.sponsor.vo.SponsorVo;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.global.common.event.Event;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SponsorService {

    private final SponsorRepository sponsorRepository;
    private final SponsorMapper sponsorMapper;
    private final AwsS3UrlHandler awsS3UrlHandler;

    @Transactional
    public void createSponsor(SponsorVo sponsorVo) {
        Sponsor newSponsor = Sponsor.from(sponsorVo);
        sponsorRepository.save(newSponsor);
    }

    @Transactional(readOnly = true)
    public GetAllSponsorsResponse getAllSponsors(int pageNum, int limit) {
        //페이징 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("id").descending());   //최신순

        Page<Sponsor> pageSponsors = sponsorRepository.findAll(pageRequest);
        //페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, pageSponsors.getTotalPages(), pageSponsors.getTotalElements());
        //dto
        GetAllSponsorsResponse response = sponsorMapper.toManagementsPage(pageSponsors.getContent(), pageInfo);

        return response;
    }

    @Transactional
    public SponsorDto updateSponsor(Long id, SponsorVo sponsorVo) {
        Sponsor findSponsor = sponsorRepository.findById(id).orElseThrow(() -> {throw SponsorNotFound.EXCEPTION;});
        findSponsor.update(sponsorVo);
        return SponsorDto.entityToDto(findSponsor);
    }

    @Transactional
    public void deleteSponsor(Long id) {
        Sponsor findSponsor = sponsorRepository.findById(id).orElseThrow(() -> {throw SponsorNotFound.EXCEPTION;});
        sponsorRepository.delete(findSponsor);
    }

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl(){
        return awsS3UrlHandler.handle("sponsors");
    }
}
