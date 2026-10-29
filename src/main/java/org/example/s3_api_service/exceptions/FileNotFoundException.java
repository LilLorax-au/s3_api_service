package org.example.s3_api_service.exceptions;

public class FileNotFoundException extends Exception{

    public FileNotFoundException(String message){
        super(message);
    }

    public FileNotFoundException(Exception e){
        super(e);
    }
}
