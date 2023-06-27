package ceos.backend.domain.awards.mapper;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.response.AwardsResponse;
import ceos.backend.domain.awards.dto.response.AllAwardsResponse;
import ceos.backend.domain.awards.helper.AwardsHelper;
import ceos.backend.domain.awards.vo.ProjectInfoVo;
import ceos.backend.global.common.dto.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AwardsMapper {

    private final AwardsHelper awardsHelper;

    public AllAwardsResponse toAwardsPage(List<Awards> awards, PageInfo pageInfo){
        List<AwardsResponse> awardsList = new ArrayList<>();
        List<ProjectInfoVo> projectsList = new ArrayList<>();

        int generation = 0;
        int generation_temp = 0;

        for(Awards award : awards){
            //award 추가
            AwardsResponse awardsResponse = AwardsResponse.to(award);
            awardsList.add(awardsResponse);

            //이미 프로젝트 리스트 추가한 경우 건너뜀
            generation = award.getGeneration();
            if(generation == generation_temp) continue;

            // project 추가
            projectsList.addAll(awardsHelper.getProjectInfo(generation));

            generation_temp = award.getGeneration();

        }
        return AllAwardsResponse.of(awardsList, projectsList, pageInfo);
    }
}
