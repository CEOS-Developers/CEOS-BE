package ceos.backend.domain.management.dto.response;

import ceos.backend.domain.management.dto.ManagementDto;
import lombok.Builder;

import java.util.List;

public class GetPartManagementsResponse {

    List<ManagementDto> managers;

    @Builder
    private GetPartManagementsResponse(List<ManagementDto> managers) {
        this.managers = managers;
    }

    public static GetPartManagementsResponse from(List<ManagementDto> managers) {
        return GetPartManagementsResponse.builder()
                .managers(managers)
                .build();
    }
}
