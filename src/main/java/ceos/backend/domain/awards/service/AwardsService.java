package ceos.backend.domain.awards.service;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.request.AwardsIdList;
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
    public void createAwards(List<AwardsRequest> awardsRequestList) {
        for(AwardsRequest awardsRequest : awardsRequestList){
            Awards awards = Awards.from(awardsRequest);
            awardsRepository.save(awards);
        }
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
        List<GenerationAwardsResponse> pageAwards = new ArrayList<>();

        if(startIndex < endIndex){
            pageAwards = generationAwardsResponses.subList(startIndex, endIndex);
        }

        //페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, totalPages, totalElements);

        return AllAwardsResponse.of(pageAwards, pageInfo);

    }

    @Transactional(readOnly = true)
    public GenerationAwardsResponse getGenerationAwards(int generation) {
        GenerationAwardsResponse generationAwardsResponse = GenerationAwardsResponse.of(generation, awardsHelper.getAwardsDto(generation), awardsHelper.getProjectVo(generation));
        return generationAwardsResponse;
    }

    @Transactional
    public void updateAwards(int generation, List<AwardsRequest> awardsRequestList) {
        //기존 데이터 삭제
        List<Awards> awardsList = awardsRepository.findByGeneration(generation);
        awardsRepository.deleteAllInBatch(awardsList);

        //수정된 데이터 넣기
        createAwards(awardsRequestList);
    }

    @Transactional
    public void deleteAward(AwardsIdList awardsIdList){
        awardsRepository.deleteAllByIdInBatch(awardsIdList.getAwardsIdList());
    }
}
