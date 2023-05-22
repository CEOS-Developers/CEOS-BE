package ceos.backend.domain.sponsor.service;

import ceos.backend.global.common.dto.AwsS3Url;
import ceos.backend.global.common.event.Event;
import ceos.backend.infra.s3.AwsS3UrlHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SponsorService {

    private final AwsS3UrlHandler awsS3UrlHandler;

    @Transactional(readOnly = true)
    public AwsS3Url getImageUrl(){
        return awsS3UrlHandler.handle("sponsors");
    }
}
