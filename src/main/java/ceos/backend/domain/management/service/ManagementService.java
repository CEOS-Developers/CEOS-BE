package ceos.backend.domain.management.service;


import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.domain.ManagementRole;
import ceos.backend.domain.management.dto.ManagementDto;
import ceos.backend.domain.management.dto.request.CreateManagementRequest;
import ceos.backend.domain.management.dto.request.UpdateManagementRequest;
import ceos.backend.domain.management.dto.response.GetAllManagementsResponse;
import ceos.backend.domain.management.dto.response.GetAllPartManagementsResponse;
import ceos.backend.domain.management.exception.ManagerNotFound;
import ceos.backend.domain.management.helper.ManagementHelper;
import ceos.backend.domain.management.mapper.ManagementMapper;
import ceos.backend.domain.management.repository.ManagementRepository;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.dto.PageInfo;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementService {

    private final ManagementRepository managementRepository;
    private final ManagementMapper managementMapper;
    private final ManagementHelper managementHelper;
    private final AwsS3UrlHandler awsS3UrlHandler;

    @Transactional
    public void createManagement(CreateManagementRequest createManagementRequest) {
        Management newManagement =
                managementMapper.toEntity(createManagementRequest.getManagementVo());
        managementRepository.save(newManagement);
    }

    @Transactional(readOnly = true)
    public GetAllManagementsResponse getAllManagements(int pageNum, int limit) {
        // 페이징 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("id").ascending());

        Page<Management> pageManagements = managementRepository.findAll(pageRequest);
        // 페이징 정보
        PageInfo pageInfo =
                PageInfo.of(
                        pageNum,
                        limit,
                        pageManagements.getTotalPages(),
                        pageManagements.getTotalElements());
        // dto
        GetAllManagementsResponse response =
                managementMapper.toManagementsPage(pageManagements.getContent(), pageInfo);

        return response;
    }

    @Transactional(readOnly = true)
    public GetAllPartManagementsResponse getAllPartManagements() {
        List<Management> findPresidency =
                managementRepository.findManagementAllByRoleOrderByNameAsc(
                        ManagementRole.PRESIDENCY);
        List<Management> findAdvisors =
                managementRepository.findManagementAllByRoleOrderByNameAsc(ManagementRole.ADVISOR);
        List<Management> findGeneralAffairs =
                managementRepository.findManagementAllByRoleOrderByNameAsc(
                        ManagementRole.GENERAL_AFFAIRS);
        List<Management> findPartLeaders =
                managementRepository.findManagementAllByRoleOrderByNameAsc(
                        ManagementRole.PART_LEADER);
        List<Management> findManagements =
                managementRepository.findManagementAllByRoleOrderByNameAsc(
                        ManagementRole.MANAGEMENT);

        GetAllPartManagementsResponse response =
                managementMapper.toPartManagementList(
                        findPresidency,
                        findAdvisors,
                        findGeneralAffairs,
                        findPartLeaders,
                        findManagements);

        return response;
    }

    @Transactional
    public ManagementDto getManagement(Long id) {
        Management findManagement =
                managementRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw ManagerNotFound.EXCEPTION;
                                });
        return ManagementDto.entityToDto(findManagement);
    }

    @Transactional
    public ManagementDto updateManagementInfo(
            Long id, UpdateManagementRequest updateManagementRequest) {
        Management findManagement =
                managementRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw ManagerNotFound.EXCEPTION;
                                });
        findManagement.update(updateManagementRequest);
        return ManagementDto.entityToDto(findManagement);
    }

    @Transactional
    public void deleteManagement(Long id) {
        Management findManagement =
                managementRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw ManagerNotFound.EXCEPTION;
                                });
        managementRepository.delete(findManagement);
    }

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl() {
        return awsS3UrlHandler.handle("managements");
    }
}
