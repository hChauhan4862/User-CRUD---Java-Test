package com.keenable.assignment1.keenable_hc.model;

import org.springframework.web.multipart.MultipartFile;

public class UserBulkUploadRequest {
    private MultipartFile file;

    // Getters and Setters
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}