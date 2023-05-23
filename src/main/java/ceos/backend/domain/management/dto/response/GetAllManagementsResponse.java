package ceos.backend.domain.management.dto.response;

import ceos.backend.domain.management.domain.Management;
import ceos.backend.global.common.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class GetAllManagementsResponse {

    List<Management> managers;
    PageInfo pageInfo;

    @Builder
    private GetAllManagementsResponse(List<Management> managers, PageInfo pageInfo) {
        this.managers = managers;
        this.pageInfo = pageInfo;
    }

    public static GetAllManagementsResponse of(List<Management> managers, PageInfo pageInfo) {
        return GetAllManagementsResponse.builder()
                .managers(managers)
                .pageInfo(pageInfo)
                .build();
    }
}
