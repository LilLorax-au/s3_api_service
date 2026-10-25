package org.example.s3_api_service.controllers;

import org.example.s3_api_service.services.FileService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @GetMapping("/file/download")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam(required = false) String fileName) {
        try{
            byte[] fileBytes = FileService.download().content();
            System.out.println(fileBytes.length);
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body(fileBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
