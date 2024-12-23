package com.goodtown.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.mapper.AdminMapper;
import com.goodtown.pojo.Admin;
import com.goodtown.utils.JwtHelper;
import com.goodtown.utils.MD5Util;
import com.goodtown.utils.Result;
import com.goodtown.utils.ResultCodeEnum;

@SuppressWarnings("rawtypes")
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Result adminlogin(Admin admin) {
        Admin loginAdmin = (Admin) redisTemplate.opsForValue().get("admin:" + admin.getUsername());

        if (loginAdmin == null) {
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Admin::getUsername, admin.getUsername());
            loginAdmin = adminMapper.selectOne(queryWrapper);

            if (loginAdmin == null) {
                return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
            }

            redisTemplate.opsForValue().set("admin:" + admin.getUsername(), loginAdmin);
            redisTemplate.expire("admin:" + admin.getUsername(), 360, TimeUnit.SECONDS);
        }

        if (!StringUtils.isEmpty(admin.getPassword())
                && loginAdmin.getPassword().equals(MD5Util.encrypt(admin.getPassword()))) {
            String token = jwtHelper.createToken(Long.valueOf(loginAdmin.getUsername()));

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            return Result.ok(data);
        }

        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }



}