package com.rest_api.fs14backend.utils;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {


    public AmazonConfig(@Value("${AWSSECRETACCESSKEY}") String awsSecretAccessKey, @Value("${AWSACCESSKEYID}") String awsAccessKeyId) {
        this.awsSecretAccessKey = awsSecretAccessKey;
        this.awsAccessKeyId = awsAccessKeyId;
    }
    private final String awsAccessKeyId;
    private final String awsSecretAccessKey;
    private final Region awsRegion = Region.EU_NORTH_1;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey);
        return S3Client.builder()
                .region(awsRegion)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}