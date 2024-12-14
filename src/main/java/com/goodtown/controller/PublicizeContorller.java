package com.goodtown.controller;
import com.goodtown.pojo.TownPromotional;
import com.goodtown.service.PromotionalTypeServiceImpl;
import com.goodtown.service.PublicizeService;
import com.goodtown.service.TownInfoServiceImpl;
import com.goodtown.utils.Result;
import com.goodtown.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/publicize")
@CrossOrigin(origins = "*")
public class PublicizeContorller {
    @Autowired
    private UploadFile uploadFile;

    @Autowired
    private PublicizeService publicizeService;

    @Autowired
    private PromotionalTypeServiceImpl promotionalTypeService;

    @Autowired
    private TownInfoServiceImpl townInfoService;

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
    @PostMapping("submit")
    public Result submit(@RequestBody TownPromotional data){
        return publicizeService.submit(data);
    }

    @GetMapping("getTypeList")
    public Result getTypeList(){
        return Result.ok(promotionalTypeService.getTypeList());
    }

    @GetMapping("getTownList")
    public Result getTownList(){
        return Result.ok(townInfoService.getTownList());
    }

    @GetMapping("getPromotionalList")
    public Result getPromotionalList(){
        return Result.ok(publicizeService.getAll());
    }

    @GetMapping("detail")
    public Result getDetail(@RequestParam String id) {
        return publicizeService.getDetail(id);
    }
}
