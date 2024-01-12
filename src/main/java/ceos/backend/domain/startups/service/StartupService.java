package ceos.backend.domain.startups.service;

import ceos.backend.domain.startups.domain.Startup;
import ceos.backend.domain.startups.dto.request.StartupRequest;
import ceos.backend.domain.startups.dto.response.StartupResponse;
import ceos.backend.domain.startups.dto.response.StartupsResponse;
import ceos.backend.domain.startups.exception.StartupNotFound;
import ceos.backend.domain.startups.repository.StartupRepository;
import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StartupService {

    private final StartupRepository startupRepository;
    private final AwsS3UrlHandler awsS3UrlHandler;

    @Transactional
    public void createStartup(StartupRequest startupRequest) {
        startupRepository.save(startupRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public StartupsResponse getStartups(Integer pageNum, Integer limit) {
        PageRequest pageRequest = PageRequest.of(pageNum, limit, Sort.by("id").descending());

        Page<Startup> startups = startupRepository.findAll(pageRequest);

        return StartupsResponse.fromPageable(startups);
    }

    @Transactional(readOnly = true)
    public StartupResponse getStartup(Long startupId) {
        Startup startup = startupRepository.findById(startupId).orElseThrow(
                () -> StartupNotFound.EXCEPTION
        );

        return StartupResponse.fromEntity(startup);
    }

    @Transactional
    public StartupResponse updateStartup(Long startupId, StartupRequest startupRequest) {
        Startup startup = startupRepository.findById(startupId).orElseThrow(
                () -> StartupNotFound.EXCEPTION
        );

        startup.update(startupRequest);
        return StartupResponse.fromEntity(startup);
    }

    @Transactional
    public void deleteStartup(Long startupId) {
        Startup startup = startupRepository.findById(startupId).orElseThrow(
                () -> StartupNotFound.EXCEPTION
        );

        startupRepository.delete(startup);
    }

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl() {
        return awsS3UrlHandler.handle("startups");
    }

}
