package com.goodtown.controller;

import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.pojo.TownSupport;
import com.goodtown.service.SupportService;
import com.goodtown.utils.Result;
import com.goodtown.utils.UploadFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/support")
@CrossOrigin(origins = "*")
public class SupportController {
    @Autowired
    private UploadFile uploadFile;

    @Autowired
    private SupportService supportService;

    @PostMapping("uploadImage")
    public Result<String> uploadImage(@RequestPart MultipartFile file) throws IOException {
        return uploadFile.upload(file);
    }

    @PostMapping("uploadVideo")
    public Result<String> uploadVideo(@RequestPart MultipartFile file) throws IOException {
        return uploadFile.upload(file);
    }

    @PostMapping("/submit")
    public Result submitSupport(@RequestBody TownSupport data, 
                              @RequestHeader(value = "token", required = false) String token) {
        return supportService.submitSupport(data, token);
    }

    @PutMapping("/update")
    public Result updateSupport(@RequestBody Map<String, Object> data, 
                              @RequestHeader(value = "token", required = false) String token) {
        return supportService.updateSupport(data, token);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteSupport(@PathVariable String id, 
                              @RequestParam Long userId,
                              @RequestHeader(value = "token", required = false) String token) {
        return supportService.deleteSupport(id, userId, token);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable String id) {
        return supportService.getDetail(id);
    }

    @GetMapping("/list/{pid}")
    public Result getSupportsList(@PathVariable String pid) {
        return supportService.getSupportsByPromotionalId(pid);
    }
    @PostMapping("/handle")
    public Result handleSupport(@RequestParam String supportId, 
                          @RequestParam Integer action,
                          @RequestHeader("token") String token) {
        Long userId = LoginProtectInterceptor.getUserId();
        if (userId == null) {
            return Result.build(null, 400, "请先登录");
        }
        return supportService.handleSupport(supportId, action, userId, token);
    }

    @GetMapping("/mylist/{uid}")
    public Result getMySupportsList(@RequestHeader("token") String token) {
        Long uid = LoginProtectInterceptor.getUserId();
        return supportService.getMySupportsList(uid);
    }
}
