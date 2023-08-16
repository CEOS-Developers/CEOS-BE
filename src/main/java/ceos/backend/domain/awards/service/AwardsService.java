package ceos.backend.domain.awards.service;


import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.domain.StartDate;
import ceos.backend.domain.awards.dto.request.AwardsRequest;
import ceos.backend.domain.awards.dto.response.AllAwardsResponse;
import ceos.backend.domain.awards.dto.response.GenerationAwardsResponse;
import ceos.backend.domain.awards.helper.AwardsHelper;
import ceos.backend.domain.awards.repository.AwardsRepository;
import ceos.backend.domain.awards.repository.StartDateRepository;
import ceos.backend.domain.project.repository.ProjectRepository;
import ceos.backend.global.common.dto.PageInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        // 이미 기수의 수상 정보가 있다면 다시 추가할 수 없음
        awardsHelper.validateGeneration(awardsRequest.getGeneration());

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
        for (int i = maxGeneration; i >= 0; i--) {
            if (i <= 9 && i >= 1) continue;
            Optional<StartDate> s = startDateRepository.findById(i);
            String startDate = null;
            if (s.isPresent()) {
                startDate = s.get().getStartDate();
            }
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
        Optional<StartDate> s = startDateRepository.findById(generation);
        String startDate = null;
        if (s.isPresent()) {
            startDate = s.get().getStartDate();
        }
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
        Optional<StartDate> startDate = startDateRepository.findById(awardsRequest.getGeneration());
        if (startDate.isEmpty()) {
            // 활동 시작 시기 저장
            StartDate s = StartDate.from(awardsRequest);
            startDateRepository.save(s);
        } else { // 활동 시작 시기 수정
            startDate.get().updateStartDate(awardsRequest.getStartDate());
        }

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
