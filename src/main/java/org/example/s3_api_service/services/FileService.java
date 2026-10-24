package org.example.s3_api_service.services;

import jakarta.annotation.Nullable;
import org.example.s3_api_service.records.File;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.io.IOException;

public class FileService {

    private static final AtomicLong fileCounter = new AtomicLong();

    @Nullable
    public static File download(){
        return null;
    }

    @Nullable
    private static File testFileBuilder(String ext){
        String file_name = "test_file_" + fileCounter.toString();

        java.io.File testFile = new java.io.File(file_name + ext);

        if (testFile.exists()){}


        try{
            return new File(
                    file_name,
                    ext,
                    org.apache.commons.io.FileUtils.readFileToByteArray(testFile)
            );
        } catch (IOException e) {
            return null;
        }

    }

    private java.io.File testWriteFile(java.io.File){

    }


}
