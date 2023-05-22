package ceos.backend.domain.management.mapper;

import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.dto.request.CreateManagementRequest;
import ceos.backend.domain.management.vo.ManagementVo;
import org.springframework.stereotype.Component;

@Component
public class ManagementMapper {
    public Management toEntity(ManagementVo managementVo) {
        return Management.from(managementVo);
    }
}
