package ceos.backend.domain.management.dto.response;

import ceos.backend.domain.management.dto.ManagementDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetAllPartManagementsResponse {

    List<ManagementDto> presidency;
    List<ManagementDto> generalAffairs;
    List<ManagementDto> partLeaders;
    List<ManagementDto> managers;

    @Builder
    public GetAllPartManagementsResponse(
            List<ManagementDto> presidency,
            List<ManagementDto> generalAffairs,
            List<ManagementDto> partLeaders,
            List<ManagementDto> managers
    ) {
        this.presidency = presidency;
        this.generalAffairs = generalAffairs;
        this.partLeaders = partLeaders;
        this.managers = managers;
    }

    public static GetAllPartManagementsResponse of(List<ManagementDto> presidency, List<ManagementDto> generalAffairs, List<ManagementDto> partLeaders, List<ManagementDto> managers) {
        return GetAllPartManagementsResponse.builder()
                .presidency(presidency)
                .generalAffairs(generalAffairs)
                .partLeaders(partLeaders)
                .managers(managers)
                .build();
    }
}
