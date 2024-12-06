package com.goodtown.controller;
import com.goodtown.utils.Result;
import com.goodtown.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/publicize")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicizeContorller {
    @Autowired
    private UploadFile uploadFile;

    @GetMapping("test")
    public String test(){
        return "test";
    }

    @PostMapping("uploadImage")
    public Result<String> uploadImage(@RequestPart MultipartFile file) throws IOException {
        return uploadFile.upload(file);
    }
    @PostMapping("uploadVideo")
    public Result<String> uploadVideo(@RequestPart MultipartFile file) throws IOException {
        return uploadFile.upload(file);
    }
}
