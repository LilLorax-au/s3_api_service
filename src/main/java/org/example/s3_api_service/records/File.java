package org.example.s3_api_service.records;

public record File(String fileName, String extension, byte[] content) {}
