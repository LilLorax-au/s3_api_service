package org.example.s3_api_service.controllers;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.example.s3_api_service.services.FileService;
import org.example.s3_api_service.records.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    /**
     * End point for downloading files
     * @param fileName to spec what file to download
     * TODO param key, for secure api auth
     * @return A resource to download, will end up supporting any files, but limited to byte array size of about 2gb
     */
    @GetMapping("/file/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam(required = false) String fileName) {

//      TODO build auth service that's key based, assure btyeArray method is best opt here
//      TODO expand for larger files, create more dynamic approach
        try{
            File file = FileService.download();
            Resource resource = new ByteArrayResource(file.content());
            String contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + (file.fileName() + file.extension()) )
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
