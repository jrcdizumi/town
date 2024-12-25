package com.goodtown.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.mapper.AdminMapper;
import com.goodtown.mapper.ReportMapper;
import com.goodtown.mapper.SupportMapper;
import com.goodtown.mapper.PublicizeMapper;
import com.goodtown.pojo.Admin;
import com.goodtown.pojo.Report;
import com.goodtown.pojo.TownPromotional;
import com.goodtown.pojo.TownSupport;
import com.goodtown.utils.JwtHelper;
import com.goodtown.utils.MD5Util;
import com.goodtown.utils.Result;
import com.goodtown.utils.ResultCodeEnum;


@SuppressWarnings("rawtypes")
public interface AdminService {

    Result adminlogin(Admin admin);
    Result getStatistics(String startDate, String endDate, String townID);
    Result countTownPromotionalAndSupport(String townID);
}
