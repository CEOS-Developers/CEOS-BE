package ceos.backend.infra.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwsS3UrlGenerator {
    @Value("${aws.s3.bucket}")
    private String BUCKET;
    private final AwsS3Config awsS3Config;

    public String generateUrl(String prefix){
        String filename = UUID.randomUUID().toString();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Cache-Control", "no-cache");

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(prefix + "/" + filename)
                .metadata(metadata)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = awsS3Config.s3Presigner().presignPutObject(presignRequest);

        return presignedRequest.url().toString();
    }
}
