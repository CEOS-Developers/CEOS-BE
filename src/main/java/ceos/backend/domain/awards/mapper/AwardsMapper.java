package ceos.backend.domain.awards.mapper;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.response.AwardsResponse;
import ceos.backend.domain.awards.dto.response.AllAwardsResponse;
import ceos.backend.domain.awards.dto.response.GenerationAwardsResponse;
import ceos.backend.domain.awards.helper.AwardsHelper;
import ceos.backend.domain.awards.vo.ProjectInfoVo;
import ceos.backend.domain.project.repository.ProjectRepository;
import ceos.backend.global.common.dto.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AwardsMapper {

    private final AwardsHelper awardsHelper;
    private final ProjectRepository projectRepository;

    public void toAwardsPage(List<Awards> awards, PageInfo pageInfo){
//        List<AwardsResponse> awardsList = new ArrayList<>();
//        List<ProjectInfoVo> projectsList = new ArrayList<>();
//        int generation =
//
//        for(Awards award : awards){
//            //award 추가
//            GenerationAwardsResponse generationAwardsResponse = GenerationAwardsResponse.of()
//            awardsList.add(generationAwardsResponse);
//
//            int maxGeneration = projectRepository.findFirstByOrderByGenerationDesc();
//
//            // project 추가
//            projectsList.addAll(awardsHelper.getProjectInfo(generation));
//
//        }
//        return AllAwardsResponse.of(awardsList, projectsList, pageInfo);
    }
}
