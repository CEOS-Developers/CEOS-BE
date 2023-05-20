package ceos.backend.infra.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class AwsS3Config {
    @Value("${aws.s3.access-key}")
    private String AWS_ACCESS_KEY_ID;

    @Value("${aws.s3.secret-key}")
    private String AWS_SECRET_KEY;

    @Bean
    public S3Client s3Client() {
        final AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        final StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        return S3Client.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
