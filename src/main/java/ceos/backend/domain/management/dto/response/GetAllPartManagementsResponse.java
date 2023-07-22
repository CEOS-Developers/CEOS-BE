package ceos.backend.domain.management.dto.response;


import ceos.backend.domain.management.dto.ManagementDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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
            List<ManagementDto> managers) {
        this.presidency = presidency;
        this.generalAffairs = generalAffairs;
        this.partLeaders = partLeaders;
        this.managers = managers;
    }

    public static GetAllPartManagementsResponse of(
            List<ManagementDto> presidency,
            List<ManagementDto> generalAffairs,
            List<ManagementDto> partLeaders,
            List<ManagementDto> managers) {
        return GetAllPartManagementsResponse.builder()
                .presidency(presidency)
                .generalAffairs(generalAffairs)
                .partLeaders(partLeaders)
                .managers(managers)
                .build();
    }
}
