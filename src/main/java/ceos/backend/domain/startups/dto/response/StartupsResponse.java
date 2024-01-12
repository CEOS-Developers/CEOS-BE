package ceos.backend.domain.startups.dto.response;

import ceos.backend.domain.startups.domain.Startup;
import ceos.backend.global.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class StartupsResponse {

    public List<StartupsElement> startups;
    public PageInfo pageInfo;

    public static StartupsResponse fromPageable(Page<Startup> startups) {
        return new StartupsResponse(
                startups.map(StartupsElement::fromEntity).toList(),
                PageInfo.of(
                        startups.getNumber(),
                        startups.getSize(),
                        startups.getTotalPages(),
                        startups.getTotalElements()
                )
        );
    }

}
