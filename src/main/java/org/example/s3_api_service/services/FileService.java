package org.example.s3_api_service.services;

import org.example.s3_api_service.exceptions.BadKeyException;
import org.example.s3_api_service.models.File;
import org.example.s3_api_service.exceptions.AuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import javax.swing.*;

@Service
public class FileService {
    @Value("${s3.name}")
    private String bucketName;

    private final S3Client s3Client;
    private final AuthService authenticator;
    private final AtomicLong fileCounter = new AtomicLong(0);

    public FileService(S3Client s3Client, AuthService authenticator){
        this.s3Client = s3Client;
        this.authenticator = authenticator;
    }


    public File download(String apiKey, String bucketKey) throws AuthException, BadKeyException{
        String prefix;
        byte[] fileBytes;

        try {
            prefix = this.authenticator.getPrefix(apiKey);
        }catch (AuthException e){
            throw new AuthException(e);
        }

        try {
            fileBytes = s3Client.getObjectAsBytes(
                            GetObjectRequest
                                    .builder()
                                    .bucket(bucketName)
                                    .key(prefix + "/" + bucketKey)
                                    .build())
                    .asByteArray();
        } catch (NoSuchKeyException e) {
            throw new BadKeyException("Bad Key: " + bucketKey);
        }

        return new File(
                bucketKey,
                fileBytes);
        }
}

//    @Nullable
//    private static File testFileBuilder(String ext) throws IOException{
//        String file_name = "test_file_" + fileCounter;
//
//        java.io.File testFile = new java.io.File(file_name + ext);
//
//        if (!testFile.exists()) {
//            try {
//                testWriteFile(file_name + ext);
//            }catch (IOException e){
//                throw new IOException(e);
//            }
//        }
//
//        try {
//            return new File(
//                    file_name,
//                    ext,
//                    org.apache.commons.io.FileUtils.readFileToByteArray(testFile)
//            );
//        } catch (IOException e) {
//            return null;
//        }
//
//    }
//
//    private static void testWriteFile(String file_name) throws IOException{
//        try (FileWriter newFile = new FileWriter(file_name)){
//            newFile.write(s3Name);
//            System.out.println("Name:" + s3Name);
////            for (int i = 0; i < 1000; i++){
////                newFile.write("0");
////            }
//        }catch (IOException e){
//            throw new IOException(e);
//        }
//    }



