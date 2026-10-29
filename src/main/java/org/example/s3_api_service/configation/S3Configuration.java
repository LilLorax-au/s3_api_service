package org.example.s3_api_service.configation;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class S3Configuration {
    @Value("${s3.access}")
    private String accessKey;
    @Value("${s3.sec}")
    private String secKey;
    @Value("${s3.name}")
    private String bucketName;
    @Value("${s3.zone")
    private String zone;

    @Bean
    @Scope("singleton")
    public S3Client s3Client(){
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey,secKey);

        return S3Client.builder()
                .region(Region.of(zone))
//                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
