package org.example.s3_api_service.exceptions;


public class AuthException extends Exception {


    public AuthException(String message) {
        super(message);
    }

    public AuthException(Exception e){
        super(e);
    }
}
