package ceos.backend.infra.s3;

import ceos.backend.global.common.dto.AwsS3Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AwsS3UrlHandler {

    private final AwsS3UrlGenerator awsS3UrlGenerator;

    public AwsS3Url handle(String prefix){
        final String url = awsS3UrlGenerator.generateUrl(prefix);
        return AwsS3Url.to(url);
    }
}
