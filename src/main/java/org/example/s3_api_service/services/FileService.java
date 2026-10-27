package org.example.s3_api_service.services;

import jakarta.annotation.Nullable;
import org.example.s3_api_service.clients.S3Client;
import org.example.s3_api_service.records.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.io.FileWriter;

@Service
public class FileService {
    private S3Client s3Client;

    private final AtomicLong fileCounter = new AtomicLong(0);


    public File download(){
        File file = new File("test" + fileCounter.getAndIncrement(), ".txt", new byte[1024]);

        return file;
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


}
