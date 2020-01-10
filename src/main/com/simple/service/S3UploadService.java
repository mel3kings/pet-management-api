package com.simple.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.simple.data.PetS3Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class S3UploadService implements Uploader {

    @Value("${s3.client.region}")
    private String clientRegion;
    @Value("${s3.bucket}")
    private String bucket;
    @Value("${aws.accessKeyId}")
    private String access;
    @Value("${aws.secretKey}")
    private String key;

    public String UploadFileToS3(PetS3Request petS3Request) {
        try {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access, key);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(clientRegion)
                    .build();
            String uri = petS3Request.getExternalURL();
            InputStream in = new URL(uri).openStream();
            PutObjectRequest request = new PutObjectRequest(bucket, petS3Request.getFileName(),
                    in, null);
            s3Client.putObject(request);
            return s3Client.getUrl(bucket, petS3Request.getFileName()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public void DeleteFileS3(PetS3Request petS3Request) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access, key);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(clientRegion)
                .build();

        if (petS3Request.getFileName().contains("/")) {
            String[] uriParts = petS3Request.getFileName().split("/");
            s3Client.deleteObject(new DeleteObjectRequest(bucket, uriParts[uriParts.length - 1]));
        }
    }

}
