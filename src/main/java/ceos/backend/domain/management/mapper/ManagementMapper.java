package ceos.backend.domain.management.mapper;

import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.dto.response.GetAllManagementsResponse;
import ceos.backend.domain.management.Vo.ManagementVo;
import ceos.backend.global.common.dto.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManagementMapper {
    public Management toEntity(ManagementVo managementVo) {
        return Management.from(managementVo);
    }

    public GetAllManagementsResponse toManagementsPage(List<Management> managements, PageInfo pageInfo) {
        return GetAllManagementsResponse.of(managements, pageInfo);
    }
}
