package ceos.backend.domain.management.dto.response;


import ceos.backend.domain.management.dto.ManagementDto;
import ceos.backend.global.common.dto.PageInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetAllManagementsResponse {

    List<ManagementDto> content;
    PageInfo pageInfo;

    @Builder
    private GetAllManagementsResponse(List<ManagementDto> managers, PageInfo pageInfo) {
        this.content = managers;
        this.pageInfo = pageInfo;
    }

    public static GetAllManagementsResponse of(List<ManagementDto> managers, PageInfo pageInfo) {
        return GetAllManagementsResponse.builder().managers(managers).pageInfo(pageInfo).build();
    }
}
