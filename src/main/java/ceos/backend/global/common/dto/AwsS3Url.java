package ceos.backend.global.common.dto;

import ceos.backend.infra.s3.AwsS3UrlGenerator;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsS3Url {
    private final String url;

    @Builder
    private AwsS3Url(String url) {
        this.url = url;
    }

    public static AwsS3Url to(String url) {
        return AwsS3Url.builder()
                .url(url)
                .build();
    }

}
