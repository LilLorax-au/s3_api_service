package org.example.s3_api_service.clients;

import org.example.s3_api_service.records.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * A Class to build a s3 wrapper
 */
public class S3Client {
//    See ref docs for s3 at:: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3-objects.html
//    See cred docs for s3 at:: https://www.baeldung.com/java-aws-s3
    @Value("${s3.access}")
    private String accessKey;
    @Value("${s3.sec}")
    private String secKey;
    @Value("${s3.name}")
    private String bucketName;
    @Value("${s3.zone")
    private String zone;

    public S3Client(){
        return;
    }

    File pullFile(String key){
        return null;
    }
    void pushFile(File file){
        return;
    }




}
