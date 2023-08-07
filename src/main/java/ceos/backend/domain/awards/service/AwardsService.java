package ceos.backend.domain.awards.service;


import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.domain.StartDate;
import ceos.backend.domain.awards.dto.request.AwardsRequest;
import ceos.backend.domain.awards.dto.response.AllAwardsResponse;
import ceos.backend.domain.awards.dto.response.GenerationAwardsResponse;
import ceos.backend.domain.awards.exception.StartDateNotFound;
import ceos.backend.domain.awards.helper.AwardsHelper;
import ceos.backend.domain.awards.repository.AwardsRepository;
import ceos.backend.domain.awards.repository.StartDateRepository;
import ceos.backend.domain.project.repository.ProjectRepository;
import ceos.backend.global.common.dto.PageInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwardsService {

    private final AwardsRepository awardsRepository;
    private final StartDateRepository startDateRepository;
    private final ProjectRepository projectRepository;
    private final AwardsHelper awardsHelper;

    @Transactional
    public void createAwards(AwardsRequest awardsRequest) {
        // 활동 시작 시기 저장
        StartDate startDate = StartDate.from(awardsRequest);
        startDateRepository.save(startDate);

        // 수상 내역 저장
        List<String> contentList = awardsRequest.getContent();
        for (String content : contentList) {
            Awards awards = Awards.of(awardsRequest.getGeneration(), content);
            awardsRepository.save(awards);
        }
    }

    @Transactional(readOnly = true)
    public AllAwardsResponse getAllAwards(int pageNum, int limit) {
        List<GenerationAwardsResponse> generationAwardsResponses = new ArrayList<>();

        int maxGeneration = projectRepository.findMaxGeneration();
        for (int i = maxGeneration; i > 0; i--) {
            LocalDate startDate =
                    startDateRepository
                            .findById(i)
                            .orElseThrow(
                                    () -> {
                                        throw StartDateNotFound.EXCEPTION;
                                    })
                            .getStartDate();
            GenerationAwardsResponse generationAwardsResponse =
                    GenerationAwardsResponse.of(
                            i,
                            startDate,
                            awardsHelper.getAwardsDto(i),
                            awardsHelper.getProjectVo(i));
            generationAwardsResponses.add(generationAwardsResponse);
        }

        int startIndex = pageNum * limit;
        int endIndex = Math.min(startIndex + limit, generationAwardsResponses.size());
        int totalElements = generationAwardsResponses.size();
        int totalPages = (int) Math.ceil((double) totalElements / limit);
        List<GenerationAwardsResponse> pageAwards = new ArrayList<>();

        if (startIndex < endIndex) {
            pageAwards = generationAwardsResponses.subList(startIndex, endIndex);
        }

        // 페이징 정보
        PageInfo pageInfo = PageInfo.of(pageNum, limit, totalPages, totalElements);

        return AllAwardsResponse.of(pageAwards, pageInfo);
    }

    @Transactional(readOnly = true)
    public GenerationAwardsResponse getGenerationAwards(int generation) {
        LocalDate startDate =
                startDateRepository
                        .findById(generation)
                        .orElseThrow(
                                () -> {
                                    throw StartDateNotFound.EXCEPTION;
                                })
                        .getStartDate();
        return GenerationAwardsResponse.of(
                generation,
                startDate,
                awardsHelper.getAwardsDto(generation),
                awardsHelper.getProjectVo(generation));
    }

    @Transactional
    public void updateAwards(int generation, AwardsRequest awardsRequest) {
        // 기존 수상내역 데이터 삭제
        deleteAwards(generation);

        // 활동시작시기 업데이트
        StartDate startDate =
                startDateRepository
                        .findById(generation)
                        .orElseThrow(
                                () -> {
                                    throw StartDateNotFound.EXCEPTION;
                                });
        startDate.updateStartDate(awardsRequest.getStartDate());

        // 수상 내역 저장
        List<String> contentList = awardsRequest.getContent();
        for (String content : contentList) {
            Awards awards = Awards.of(awardsRequest.getGeneration(), content);
            awardsRepository.save(awards);
        }
    }

    @Transactional
    public void deleteAwards(int generation) {
        List<Awards> awardsList = awardsRepository.findByGeneration(generation);
        awardsRepository.deleteAllInBatch(awardsList);
    }
}
