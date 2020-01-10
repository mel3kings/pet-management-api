package com.simple.service;

import com.simple.data.PetS3Request;

public interface Uploader {
    String UploadFileToS3(PetS3Request pet);

    void DeleteFileS3(PetS3Request pet);

}
