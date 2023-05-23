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

    public ManagementDto update(Management management, UpdateManagementRequest request) {
        ManagementVo managementInfo = request.getManagementVo();
        if (managementInfo.getName() != null) {
            management.setName(managementInfo.getName());
        }
        if (managementInfo.getRole() != null) {
            management.setRole(managementInfo.getRole());
        }
        if (managementInfo.getPart() != null) {
            management.setPart(managementInfo.getPart());
        }
        if (Optional.ofNullable(managementInfo.getGeneration()).orElse(0) != 0) {
            management.setGeneration(managementInfo.getGeneration());
        }
        if (Optional.ofNullable(managementInfo.getManagementGeneration()).orElse(0) != 0) {
            management.setManagementGeneration(managementInfo.getManagementGeneration());
        }
        if (managementInfo.getUniversity() != null) {
            management.setUniversity(managementInfo.getUniversity());
        }
        if (managementInfo.getMajor() != null) {
            management.setMajor(managementInfo.getMajor());
        }
        if (managementInfo.getCompany() != null) {
            management.setCompany(managementInfo.getCompany());
        }
        if (managementInfo.getImageUrl() != null) {
            management.setImageUrl(managementInfo.getImageUrl());
        }
        managementRepository.save(management);
        return ManagementDto.entityToDto(management);
    }
}
