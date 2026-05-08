package DasomwithJH.ssok.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Base64;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucket;
    private final String region;

    public S3Service(S3Client s3Client,
                     @Value("${aws.s3.bucket}") String bucket,
                     @Value("${aws.s3.region}") String region) {
        this.s3Client = s3Client;
        this.bucket = bucket;
        this.region = region;
    }

    public String uploadBase64Image(String base64, String folder) {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        String key = folder + "/" + UUID.randomUUID() + ".png";

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType("image/png")
                        .build(),
                RequestBody.fromBytes(imageBytes)
        );

        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
    }
}
