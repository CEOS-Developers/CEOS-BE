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

    public static AwsS3Url managementImageUrl(AwsS3UrlGenerator awsS3UrlGenerator) {
        return AwsS3Url.builder()
                .url(awsS3UrlGenerator.generateUrl("managements"))
                .build();
    }

    public static AwsS3Url sponsorImageUrl(AwsS3UrlGenerator awsS3UrlGenerator) {
        return AwsS3Url.builder()
                .url(awsS3UrlGenerator.generateUrl("sponsors"))
                .build();
    }

    public static AwsS3Url activityImageUrl(AwsS3UrlGenerator awsS3UrlGenerator) {
        return AwsS3Url.builder()
                .url(awsS3UrlGenerator.generateUrl("activities"))
                .build();
    }

}
