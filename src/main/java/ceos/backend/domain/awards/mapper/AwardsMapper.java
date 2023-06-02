package ceos.backend.domain.awards.mapper;

import ceos.backend.domain.awards.domain.Awards;
import ceos.backend.domain.awards.dto.AwardsDTO;
import ceos.backend.domain.awards.dto.response.GetAllAwardsResponse;
import ceos.backend.global.common.dto.PageInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AwardsMapper {
    public GetAllAwardsResponse toAwardsPage(List<Awards> awards, PageInfo pageInfo){
        List<AwardsDTO> awardsDTOList = new ArrayList<>();
        for(Awards award : awards){
            AwardsDTO awardsDTO = AwardsDTO.to(award);
            awardsDTOList.add(awardsDTO);
        }
        return GetAllAwardsResponse.of(awardsDTOList, pageInfo);
    }
}
