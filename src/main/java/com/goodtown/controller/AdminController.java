package com.goodtown.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.pojo.Admin;
import com.goodtown.service.AdminService;
import com.goodtown.utils.Result;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@SuppressWarnings("rawtypes")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("adminlogin")
    public Result adminlogin(@RequestBody Admin admin) {
        Result result = adminService.adminlogin(admin);
        System.out.println("result = " + result);
        return result;
    }
    public Result getStatistics(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String region) {
        return adminService.getStatistics(startDate, endDate, region);
    }
}