package com.example.demo.controller;

import com.example.demo.response.ResultResponse;
import com.example.demo.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/s3")
@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/uploadFile")
    public ResultResponse upload(@RequestParam("file") MultipartFile file) {
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