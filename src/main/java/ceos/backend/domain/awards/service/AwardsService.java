package ceos.backend.domain.awards.service;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.response.AwardsResponse;
import ceos.backend.domain.awards.dto.request.AwardsRequest;
import ceos.backend.domain.awards.dto.response.AllAwardsResponse;
import ceos.backend.domain.awards.dto.response.GenerationAwardsResponse;
import ceos.backend.domain.awards.exception.AwardNotFound;
import ceos.backend.domain.awards.helper.AwardsHelper;
import ceos.backend.domain.awards.repository.AwardsRepository;
import ceos.backend.domain.project.repository.ProjectRepository;
import ceos.backend.global.common.dto.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwardsService {

    private final AwardsRepository awardsRepository;
    private final ProjectRepository projectRepository;
    private final AwardsHelper awardsHelper;

    @Transactional
    public void createAwards(AwardsRequest awardsRequest) {
        Awards awards = Awards.from(awardsRequest);
        awardsRepository.save(awards);
    }

    @Transactional(readOnly = true)
    public AllAwardsResponse getAllAwards(int pageNum, int limit) {
        List<GenerationAwardsResponse> generationAwardsResponses = new ArrayList<>();

        int maxGeneration = projectRepository.findMaxGeneration();
        for(int i = maxGeneration; i > 0; i--){
            GenerationAwardsResponse generationAwardsResponse = GenerationAwardsResponse.of(i, awardsHelper.getAwardsDto(i), awardsHelper.getProjectVo(i));
            generationAwardsResponses.add(generationAwardsResponse);
        }

        int startIndex = pageNum * limit;
        int endIndex = Math.min(startIndex + limit, generationAwardsResponses.size());
        int totalElements = generationAwardsResponses.size();
        int totalPages = (int) Math.ceil((double) totalElements / limit);

        List<GenerationAwardsResponse> pageAwards = generationAwardsResponses.subList(startIndex, endIndex);

        //페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, totalPages, totalElements);

        return AllAwardsResponse.of(pageAwards, pageInfo);

    }

    @Transactional(readOnly = true)
    public AwardsResponse getAward(Long id) {
        Awards awards = awardsRepository.findById(id).orElseThrow(() -> {throw AwardNotFound.EXCEPTION;});
        return AwardsResponse.to(awards);
    }

    @Transactional
    public AwardsResponse updateAward(Long id, AwardsRequest awardsRequest) {
        Awards awards = awardsRepository.findById(id).orElseThrow(() -> {throw AwardNotFound.EXCEPTION;});
        awards.updateAward(awardsRequest);
        return AwardsResponse.to(awards);
    }

    @Transactional
    public void deleteAward(Long id){
        Awards awards = awardsRepository.findById(id).orElseThrow(() -> {throw AwardNotFound.EXCEPTION;});
        awardsRepository.delete(awards);
    }
}
