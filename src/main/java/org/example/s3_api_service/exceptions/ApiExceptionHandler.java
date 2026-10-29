package org.example.s3_api_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, String>> handleBadApiKey(AuthException error){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("ERROR", error.getMessage()));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBadBucketKey(FileNotFoundException error){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("ERROR", error.getMessage()));
    }


}
