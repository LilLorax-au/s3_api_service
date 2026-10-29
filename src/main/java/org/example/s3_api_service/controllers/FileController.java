package org.example.s3_api_service.controllers;

import org.example.s3_api_service.exceptions.AuthException;
import org.example.s3_api_service.exceptions.FileNotFoundException;
import org.example.s3_api_service.services.FileService;
import org.example.s3_api_service.models.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }


    /**
     * End point for downloading files
     * @param bucketKey to spec what file to download(using s3 syntax)
     * @param apiKey auth key
     * TODO param key, for secure api auth
     * @return A resource to download, will end up supporting any files, but limited to byte array size of about 2gb
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam String apiKey,
            @RequestParam String bucketKey
    ) throws AuthException, FileNotFoundException {

        // TODO expand for larger files, create more dynamic approach

        File file;
        Resource resource;
        String contentType = "application/octet-stream";

        try{
//          Try Download based on keys
            file = this.fileService.download(apiKey, bucketKey);
        }catch(AuthException error){
//          Will be caught by ApiExceptionHandler and returned to user
            throw new AuthException(error);
        } catch (FileNotFoundException e) {
//          Will be caught by ApiExceptionHandler and returned to user
            throw new RuntimeException(e);
        }

        resource = new ByteArrayResource(file.getContent());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + (file.getName()))
                .body(resource);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testMethod(
            @RequestParam(defaultValue = "None") String one,
            @RequestParam(defaultValue = "None") String two
    ){
        return ResponseEntity.ok()
                .body("one: " + one + " two: " + two);

    }
}
