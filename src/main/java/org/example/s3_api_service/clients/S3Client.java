package org.example.s3_api_service.clients;

import org.example.s3_api_service.records.File;

/**
 * A interface to build a s3 wrapper
 */
public interface S3Client {
//    TODO must work out if this needs o be a singleton yet or not, read through s3 sdk before
//    See ref docs for s3 at:: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3-objects.html
//    See cred docs for s3 at:: https://www.baeldung.com/java-aws-s3

    File pullFile(String key);
    void pushFile(File file);




}
