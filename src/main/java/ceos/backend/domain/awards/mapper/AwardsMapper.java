package ceos.backend.domain.awards.mapper;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.response.AwardsResponse;
import ceos.backend.domain.awards.dto.response.AllAwardsResponse;
import ceos.backend.global.common.dto.PageInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AwardsMapper {
    public AllAwardsResponse toAwardsPage(List<Awards> awards, PageInfo pageInfo){
        List<AwardsResponse> awardsList = new ArrayList<>();
        for(Awards award : awards){
            AwardsResponse awardsResponse = AwardsResponse.to(award);
            awardsList.add(awardsResponse);
        }
        return AllAwardsResponse.of(awardsList, pageInfo);
    }
}
