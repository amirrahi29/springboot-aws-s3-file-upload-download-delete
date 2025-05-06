package com.example.demo.controller;

import com.example.demo.response.ResultResponse;
import com.example.demo.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/s3")
@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
    @Operation(summary = "Upload a file to S3")
    public ResultResponse uploadFile(
            @Parameter(description = "File to upload")
            @RequestPart("file") MultipartFile file) {
        return s3Service.upload(file);
    }

    @GetMapping("/deleteFile/{file}")
    public ResultResponse deleteFile(@PathVariable("file") String file) {
        return s3Service.deleteFile(file);
    }

    @GetMapping("/download/{file}")
    public ResultResponse download(@PathVariable("file") String file) {
        return s3Service.download(file);
    }

    @GetMapping("/deleteBucket/{name}")
    public ResultResponse deleteBucket(@PathVariable("name") String name) {
        return s3Service.deleteBucket(name);
    }

}