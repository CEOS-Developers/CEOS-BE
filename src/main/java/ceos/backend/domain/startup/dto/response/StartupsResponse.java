package ceos.backend.domain.startup.dto.response;


import ceos.backend.domain.startup.domain.Startup;
import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class StartupsResponse {

    public List<StartupResponse> startups;
    public PageInfo pageInfo;

    public static StartupsResponse fromPageable(Page<Startup> startups) {
        return new StartupsResponse(
                startups.map(StartupResponse::fromEntity).toList(),
                PageInfo.of(
                        startups.getNumber(),
                        startups.getSize(),
                        startups.getTotalPages(),
                        startups.getTotalElements()));
    }
}
