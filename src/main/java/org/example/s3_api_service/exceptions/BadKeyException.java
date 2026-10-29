package org.example.s3_api_service.exceptions;

public class BadKeyException extends Exception{

    public BadKeyException(String message){
        super(message);
    }

    public BadKeyException(Exception e){
        super(e);
    }
}
