package ceos.backend.domain.awards.service;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.AwardsDto;
import ceos.backend.domain.awards.dto.request.CreateAwardsRequest;
import ceos.backend.domain.awards.dto.response.GetAllAwardsResponse;
import ceos.backend.domain.awards.exception.AwardNotFound;
import ceos.backend.domain.awards.mapper.AwardsMapper;
import ceos.backend.domain.awards.repository.AwardsRepository;
import ceos.backend.domain.management.exception.ManagerNotFound;
import ceos.backend.global.common.dto.PageInfo;
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
public class AwardsService {

    private final AwardsRepository awardsRepository;
    private final AwardsMapper awardsMapper;

    @Transactional
    public void createAwards(CreateAwardsRequest createAwardsRequest) {
        Awards awards = Awards.from(createAwardsRequest);
        awardsRepository.save(awards);
    }

    @Transactional(readOnly = true)
    public GetAllAwardsResponse getAllAwards(int pageNum, int limit) {
        //페이징 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("generation").descending());

        Page<Awards> pageAwards = awardsRepository.findAll(pageRequest);

        //페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, pageAwards.getTotalPages(), pageAwards.getTotalElements());

        return awardsMapper.toAwardsPage(pageAwards.getContent(), pageInfo);
    }

    @Transactional(readOnly = true)
    public AwardsDto getAward(Long id) {
        Awards awards = awardsRepository.findById(id).orElseThrow(() -> {throw AwardNotFound.EXCEPTION;});
        return AwardsDto.to(awards);
    }
}
