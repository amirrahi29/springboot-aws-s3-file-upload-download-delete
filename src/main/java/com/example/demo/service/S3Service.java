package com.example.demo.service;

import com.example.demo.response.MetadataResponse;
import com.example.demo.response.ResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    private final AmazonS3 s3Client;


    @Value("${aws.bucket.name}")
    private String bucketName;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public ResultResponse upload(MultipartFile file) {

        MetadataResponse metadataResponse=new MetadataResponse();
        ResultResponse response=new ResultResponse();
        try {

            File fileSave=convertMultiPartToFile(file);
            String fileName=file.getOriginalFilename();
            s3Client.putObject(bucketName,fileName,fileSave);

            metadataResponse.setCode("200");
            metadataResponse.setMessage("file upload successfully in aws s3 bucket");
            metadataResponse.setNoOfRecords("1");
            response.setMetadataResponse(metadataResponse);
            response.setResult(fileName);

            return response;
        } catch (Exception e) {
            metadataResponse.setCode("400");
            metadataResponse.setMessage("failed to upload file in s3 bucket");
            metadataResponse.setNoOfRecords("0");
            response.setMetadataResponse(metadataResponse);
            response.setResult(null);
            return response;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public ResultResponse deleteFile(String file) {
        MetadataResponse metadataResponse=new MetadataResponse();
        ResultResponse response=new ResultResponse();
        try {

            s3Client.deleteObject(bucketName,file);

            metadataResponse.setCode("200");
            metadataResponse.setMessage("file delete sucessfully");
            metadataResponse.setNoOfRecords("1");
            response.setMetadataResponse(metadataResponse);
            response.setResult(file);

            return response;
        } catch (Exception e) {
            metadataResponse.setCode("400");
            metadataResponse.setMessage("failed to delete file");
            metadataResponse.setNoOfRecords("0");
            response.setMetadataResponse(metadataResponse);
            response.setResult(null);
            return response;
        }
    }

    public ResultResponse download(String file) {
        MetadataResponse metadataResponse=new MetadataResponse();
        ResultResponse response=new ResultResponse();

        InputStream inputStream = null;
        FileOutputStream fos = null;

        try {

            S3Object s3Object  = s3Client.getObject(bucketName,file);

            inputStream = s3Object.getObjectContent();
            byte[] content = IOUtils.toByteArray(inputStream);

            String downloadDir = "C:\\AWS\\";
            File downloadedFile = new File(downloadDir, file);
            new File(downloadDir).mkdirs();

            fos = new FileOutputStream(downloadedFile);
            fos.write(content);


            metadataResponse.setCode("200");
            metadataResponse.setMessage("file download sucessfully");
            metadataResponse.setNoOfRecords("1");
            response.setMetadataResponse(metadataResponse);
            response.setResult(file);

            return response;
        } catch (Exception e) {
            metadataResponse.setCode("400");
            metadataResponse.setMessage("failed to download file");
            metadataResponse.setNoOfRecords("0");
            response.setMetadataResponse(metadataResponse);
            response.setResult(null);
            return response;
        }
    }

    public ResultResponse deleteBucket(String name) {
        MetadataResponse metadataResponse=new MetadataResponse();
        ResultResponse response=new ResultResponse();
        try {

            s3Client.deleteBucket(name);

            metadataResponse.setCode("200");
            metadataResponse.setMessage("file delete sucessfully");
            metadataResponse.setNoOfRecords("1");
            response.setMetadataResponse(metadataResponse);
            response.setResult(name);

            return response;
        } catch (Exception e) {
            metadataResponse.setCode("400");
            metadataResponse.setMessage("failed to delete file");
            metadataResponse.setNoOfRecords("0");
            response.setMetadataResponse(metadataResponse);
            response.setResult(null);
            return response;
        }
    }

}