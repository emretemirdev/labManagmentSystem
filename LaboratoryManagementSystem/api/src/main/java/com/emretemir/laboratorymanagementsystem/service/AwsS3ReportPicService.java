package com.emretemir.laboratorymanagementsystem.service;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;

@Service
public class AwsS3ReportPicService {

    private final S3Client s3Client;
    private final String bucketName;

    public AwsS3ReportPicService(@Value("${cloud.aws.credentials.access-key}") String accessKey,
                                 @Value("${cloud.aws.credentials.secret-key}") String secretKey,
                                 @Value("${cloud.aws.region.static}") String region,
                                 @Value("${custom.bucket-name}") String bucketName) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            PutObjectResponse response = s3Client.putObject(putOb, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

            URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName));
            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("S3'e dosya yüklenirken hata oluştu", e);
        }
    }
}
