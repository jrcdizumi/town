package com.goodtown.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private PublicizeMapper townPromotionalMapper;
    @Autowired
    private SupportMapper townSupportMapper;

    @Override
    public Result getStatistics(String startDate, String endDate, String townID) {
        // Update the statistics before fetching the data
        countTownPromotionalAndSupport(townID);
        startDate = startDate.substring(0, 7);
        endDate = endDate.substring(0, 7);        
        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();

        queryWrapper.between("monthID", startDate, endDate);
        // queryWrapper.like("townID", townID);
        
        List<Report> reports = reportMapper.selectList(queryWrapper);
        System.out.println("reports = " + reports);
        return Result.ok(reports);
    }

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
            String token = jwtHelper.createToken((long)1);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            return Result.ok(data);
        }

        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result countTownPromotionalAndSupport(String townID) {
        // 查询所有的 TownPromotional，条件是 pstate 为 0 且 townID 一致
        LambdaQueryWrapper<TownPromotional> promotionalQueryWrapper = new LambdaQueryWrapper<>();
        promotionalQueryWrapper.eq(TownPromotional::getPstate, 0);
        promotionalQueryWrapper.eq(TownPromotional::getTownID, townID);
        List<TownPromotional> promotionalList = townPromotionalMapper.selectList(promotionalQueryWrapper);

        // 首先查询指定townID的所有TownPromotional的pid
        LambdaQueryWrapper<TownPromotional> promotionalWrapper = new LambdaQueryWrapper<>();
        promotionalWrapper.eq(TownPromotional::getTownID, townID);
        List<TownPromotional> promotionals = townPromotionalMapper.selectList(promotionalWrapper);
        List<Integer> pids = promotionals.stream()
        .map(TownPromotional::getPid)
        .collect(Collectors.toList());

        // 然后查询TownSupport，条件是supportState为1且pid在上面查询的列表中
        LambdaQueryWrapper<TownSupport> supportQueryWrapper = new LambdaQueryWrapper<>();
        supportQueryWrapper.eq(TownSupport::getSupportState, 1)
        .in(TownSupport::getPid, pids);
        List<TownSupport> supportList = townSupportMapper.selectList(supportQueryWrapper);
        // 按月份进行计数
        Map<String, Report> reportMap = new HashMap<>();
        for (TownPromotional promotional : promotionalList) {
            String monthID = promotional.getPbegindate().toString().substring(0, 7); // 获取月份
            Report report = reportMap.getOrDefault(monthID, new Report());
            report.setMonthID(monthID);
            report.setPtypeId(promotional.getPtypeId());
            report.setTownID(promotional.getTownID().toString());
            report.setPuserNum(report.getPuserNum() == null ? 1 : report.getPuserNum() + 1);
            reportMap.put(monthID, report);
        }

        for (TownSupport support : supportList) {
            String monthID = support.getSupportDate().toString().substring(0, 7); // 获取月份
            Report report = reportMap.getOrDefault(monthID, new Report());
            report.setMonthID(monthID);
            report.setSuserNum(report.getSuserNum() == null ? 1 : report.getSuserNum() + 1);
            reportMap.put(monthID, report);
        }

        // 将结果存到 Report 里面
        for (Report report : reportMap.values()) {
            reportMapper.insertOrUpdate(report);
        }

        return Result.ok(null);
    }
}