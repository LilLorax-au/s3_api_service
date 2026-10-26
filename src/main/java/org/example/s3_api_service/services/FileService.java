package org.example.s3_api_service.services;

import jakarta.annotation.Nullable;
import org.example.s3_api_service.records.File;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.io.FileWriter;


public class FileService {

    private static final AtomicLong fileCounter = new AtomicLong();

    @Nullable
    public static File download(){
        File file = null;
        try{
            file = testFileBuilder(".pdf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    @Nullable
    private static File testFileBuilder(String ext) throws IOException{
        String file_name = "test_file_" + fileCounter.toString();

        java.io.File testFile = new java.io.File(file_name + ext);

        if (!testFile.exists()) {
            try {
                testWriteFile(file_name + ext);
            }catch (IOException e){
                throw new IOException(e);
            }
        }

        try {
            return new File(
                    file_name,
                    ext,
                    org.apache.commons.io.FileUtils.readFileToByteArray(testFile)
            );
        } catch (IOException e) {
            return null;
        }

    }

    private static void testWriteFile(String file_name) throws IOException{
        try (FileWriter newFile = new FileWriter(file_name)){
            for (int i = 0; i < 1000; i++){
                newFile.write("0");
            }
        }catch (IOException e){
            throw new IOException(e);
        }
    }


}
