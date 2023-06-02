package ceos.backend.domain.awards.mapper;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.AwardsDto;
import ceos.backend.domain.awards.dto.response.GetAllAwardsResponse;
import ceos.backend.global.common.dto.PageInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AwardsMapper {
    public GetAllAwardsResponse toAwardsPage(List<Awards> awards, PageInfo pageInfo){
        List<AwardsDto> awardsDtoList = new ArrayList<>();
        for(Awards award : awards){
            AwardsDto awardsDTO = AwardsDto.to(award);
            awardsDtoList.add(awardsDTO);
        }
        return GetAllAwardsResponse.of(awardsDtoList, pageInfo);
    }
}
