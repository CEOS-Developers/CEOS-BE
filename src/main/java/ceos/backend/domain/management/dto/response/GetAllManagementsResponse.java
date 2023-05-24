package ceos.backend.domain.management.dto.response;

import ceos.backend.domain.management.dto.ManagementDto;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetAllManagementsResponse {

    List<ManagementDto> managers;
    PageInfo pageInfo;

    @Builder
    private GetAllManagementsResponse(List<ManagementDto> managers, PageInfo pageInfo) {
        this.managers = managers;
        this.pageInfo = pageInfo;
    }

    public static GetAllManagementsResponse of(List<ManagementDto> managers, PageInfo pageInfo) {
        return GetAllManagementsResponse.builder()
                .managers(managers)
                .pageInfo(pageInfo)
                .build();
    }
}
