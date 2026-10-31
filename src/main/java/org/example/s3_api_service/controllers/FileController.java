package org.example.s3_api_service.controllers;

import org.example.s3_api_service.exceptions.AuthException;
import org.example.s3_api_service.exceptions.FileNotFoundException;
import org.example.s3_api_service.exceptions.FileOverwriteException;
import org.example.s3_api_service.models.CleanedS3Object;
import org.example.s3_api_service.services.FileService;
import org.example.s3_api_service.models.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * End point for downloading files
     *
     * @param bucketKey to spec what file to download(using s3 syntax)
     * @param apiKey    auth key
     *                  TODO param key, for secure api auth
     * @return A resource to download, will end up supporting any files, but limited to byte array size of about 2gb
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestHeader String apiKey,
            @RequestParam String bucketKey,
            @RequestParam boolean isShared
    ) throws AuthException, FileNotFoundException {

        // TODO expand for larger files, create more dynamic approach

        File file;
        Resource resource;
        String contentType = "application/octet-stream";

        try {
//          Try Download based on keys
            file = this.fileService.download(apiKey, bucketKey, isShared);
        } catch (AuthException error) {
//          Will be caught by ApiExceptionHandler and returned to user
            throw new AuthException(error);
        } catch (FileNotFoundException e) {
//          Will be caught by ApiExceptionHandler and returned to user
            throw new FileNotFoundException(e);
        }

        resource = new ByteArrayResource(file.getContent());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + (file.getName()))
                .body(resource);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CleanedS3Object>> listFiles(
            @RequestHeader String apiKey,
            @RequestParam boolean showShared
    ) throws AuthException {
        List<CleanedS3Object> body;

        try {
            body = fileService.list(apiKey, showShared);
        } catch (AuthException e) {
            throw new AuthException(e);
        }

        return ResponseEntity.ok()
                .body(body);

    }

    @PostMapping
    public ResponseEntity<String> uploadFile(
            @RequestHeader String apiKey,
            @RequestParam boolean isShared,
            @RequestParam boolean overwrite,
            @RequestBody MultipartFile file
            ) throws FileOverwriteException, RuntimeException, AuthException{

        try{
            fileService.upload(apiKey, isShared, overwrite, new File(file));
        } catch (FileOverwriteException e){
            throw new FileOverwriteException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthException e) {
            throw new AuthException(e);
        }

        return ResponseEntity.ok("Upload status: Okay");
    }
}

