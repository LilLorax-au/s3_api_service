package org.example.s3_api_service.models;

public record CleanedS3Object(String key, long size, java.time.Instant lastModified) {
}
