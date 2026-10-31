package org.example.s3_api_service.services;

import org.example.s3_api_service.exceptions.FileNotFoundException;
import org.example.s3_api_service.exceptions.FileOverwriteException;
import org.example.s3_api_service.models.CleanedS3Object;
import org.example.s3_api_service.models.File;
import org.example.s3_api_service.exceptions.AuthException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.stream.Collectors;


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
                if (authenticator.isKeyValid(apiKey)) {
                    prefix = "share";
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
                prefix = authenticator.getPrefix(apiKey);
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
                                    .key(prefix + "\\" + bucketKey)
                                    .build())
                    .asByteArray();
        } catch (NoSuchKeyException e) {
            throw new FileNotFoundException("Bad Bucket Key: " + bucketKey);
        }

        return new File(
                bucketKey,
                fileBytes);
    }

    public List<CleanedS3Object> list(String apiKey, boolean showShared) throws AuthException{
        List<CleanedS3Object> contents = null;
        String prefix;

        if (showShared){
            try{
                if(authenticator.isKeyValid(apiKey)){
                    prefix = "share";
                    contents = list(prefix);
                }
            } catch (AuthException e) {
                throw new AuthException(e);
            }
        }
        else{
            try{
                prefix = authenticator.getPrefix(apiKey);
                contents = list(prefix);
            } catch (AuthException e){
                throw new AuthException(e);
            }
        }
        return contents;
    }

    private List<CleanedS3Object> list(String prefix) throws AuthException {
//        TODO, in the case of network problems or aws downtime, need to write in error handling for it
        List<CleanedS3Object> contents;

        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request
                .builder()
                .bucket(bucketName)
                .prefix(prefix + "\\")
                .build();
        contents = cleanS3Objects(prefix, listObjectsV2Request);

        return contents;
    }

    private List<CleanedS3Object> cleanS3Objects(String prefix, ListObjectsV2Request rawContent){

        return s3Client.listObjectsV2Paginator(rawContent)
                .stream()
                .flatMap(response -> response.contents().stream())
                .filter(obj -> !obj.key().equals(prefix + "\\"))
                .map(obj -> new CleanedS3Object(
                        obj.key().substring(prefix.length())
                        ,obj.size()
                        ,obj.lastModified()
                ))
                .toList();
    }

    public void upload(
            String apiKey,
            boolean isShared,
            boolean overwrite,
            File file
    ) throws AuthException{
        String prefix;

        if(isShared){
            try {
                if(authenticator.isKeyValid(apiKey)) {
                    prefix = "share";
                }
            } catch (AuthException e) {
                throw new AuthException(e);
            }
        }
        else{
            try{
                prefix = authenticator.getPrefix(apiKey);
            } catch (AuthException e) {
                throw new AuthException(e);
            }
        }
    }
    private void upload(String prefix, boolean overwrite, File file)throws FileOverwriteException{
        PutObjectRequest request;

        if(overwrite){
            request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(prefix + "\\" + file.getName())
                    .build();
        }else{
            request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(prefix + "\\" + file.getName())
                    .ifNoneMatch("*")
                    .build();
        }
        try{
            s3Client.putObject(request, RequestBody.fromBytes(file.getContent()));
        } catch (S3Exception e){
            if(e.statusCode() == 412){
                throw new FileOverwriteException(e);
            }
        }
    }

}
