package ceos.backend.domain.retrospect.dto.response;


import ceos.backend.domain.retrospect.domain.Retrospect;
import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class GetRetrospectsResponse {
    public List<GetRetrospectsElement> retrospects;
    public PageInfo pageInfo;

    public static GetRetrospectsResponse fromPageable(Page<Retrospect> retrospects) {
        return new GetRetrospectsResponse(
                retrospects.map(GetRetrospectsElement::fromEntity).toList(),
                PageInfo.of(
                        retrospects.getNumber(),
                        retrospects.getSize(),
                        retrospects.getTotalPages(),
                        retrospects.getTotalElements()));
    }
}
