package ceos.backend.domain.management.helper;

import ceos.backend.domain.management.Vo.ManagementVo;
import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.dto.ManagementDto;
import ceos.backend.domain.management.dto.request.UpdateManagementRequest;
import ceos.backend.domain.management.repository.ManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagementHelper {
    private final ManagementRepository managementRepository;
}
