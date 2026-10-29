package org.example.s3_api_service.services;

import org.example.s3_api_service.exceptions.FileNotFoundException;
import org.example.s3_api_service.models.File;
import org.example.s3_api_service.exceptions.AuthException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Service
public class FileService {
    @Value("${s3.name}")
    private String bucketName;

    private final S3Client s3Client;
    private final AuthService authenticator;

    public FileService(S3Client s3Client, AuthService authenticator){
        this.s3Client = s3Client;
        this.authenticator = authenticator;
    }


    public File download(String apiKey, String bucketKey, boolean isShared) throws AuthException, FileNotFoundException {
        File newFile = null;
        String prefix;

        if (isShared) {
            try {
                if (this.authenticator.isKeyValid(apiKey)) {
                    prefix = "share/";
                    newFile = download(prefix, bucketKey);
                }
            } catch (AuthException e) {
                throw new AuthException(e);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(e);
            }
        }
        else {
            try {
                prefix = this.authenticator.getPrefix(apiKey);
                newFile = download(prefix, bucketKey);
            } catch (AuthException e) {
                throw new AuthException(e);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(e);
            }
        }
        return newFile;
    }


    private File download(String prefix, String bucketKey) throws FileNotFoundException{
        byte[] fileBytes;
        try {
            fileBytes = s3Client.getObjectAsBytes(
                            GetObjectRequest
                                    .builder()
                                    .bucket(bucketName)
                                    .key(prefix + "/" + bucketKey)
                                    .build())
                    .asByteArray();
        } catch (NoSuchKeyException e) {
            throw new FileNotFoundException("Bad Bucket Key: " + bucketKey);
        }

        return new File(
                bucketKey,
                fileBytes);
    }
}