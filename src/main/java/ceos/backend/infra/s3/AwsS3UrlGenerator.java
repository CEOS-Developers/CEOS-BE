package ceos.backend.infra.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwsS3UrlGenerator {
    @Value("${aws.s3.bucket}")
    private String BUCKET;
    private final AwsS3Config awsS3Config;

    public String generateUrl(String prefix){
        String filename = UUID.randomUUID().toString();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(prefix + "/" + filename)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = awsS3Config.s3Presigner().presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toString();
    }
}
