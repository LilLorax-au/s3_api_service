package org.example.s3_api_service.exceptions;

public class FileOverwriteException extends RuntimeException {
    public FileOverwriteException(String message) {
        super(message);
    }

    public FileOverwriteException(Exception e){
        super(e);
    }
}
