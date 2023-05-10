package ceos.backend.infra.ses;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesAsyncClient;

@Configuration
public class AwsSESConfig {
    @Value("${aws.access-key}")
    private String AWS_ACCESS_KEY_ID;

    @Value("${aws.secret-key}")
    private String AWS_SECRET_KEY;

    @Bean
    public SesAsyncClient sesAsyncClient() {
        final AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        final StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        return SesAsyncClient.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
