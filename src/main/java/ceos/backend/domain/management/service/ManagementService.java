package ceos.backend.domain.management.service;

import ceos.backend.domain.management.domain.Management;
import ceos.backend.domain.management.dto.request.CreateManagementRequest;
import ceos.backend.domain.management.mapper.ManagementMapper;
import ceos.backend.domain.management.repository.ManagementRepository;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementService {

    private final ManagementRepository managementRepository;
    private final ManagementMapper managementMapper;
    private final AwsS3UrlHandler awsS3UrlHandler;

    @Transactional
    public void createManagement(CreateManagementRequest createManagementRequest) {
        Management newManagement = managementMapper.toEntity(createManagementRequest.getManagementVo());
        managementRepository.save(newManagement);
    }

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl(){
        return awsS3UrlHandler.handle("managements");
    }
}
