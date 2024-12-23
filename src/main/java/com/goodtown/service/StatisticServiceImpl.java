package com.goodtown.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodtown.mapper.ReportMapper;
import com.goodtown.pojo.Report;
import com.goodtown.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl extends ServiceImpl<ReportMapper, Report> implements StatisticService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public Result getStatistics(String startDate, String endDate, String region) {
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("monthID", startDate, endDate);
        queryWrapper.like("townID", region);

        List<Report> reports = reportMapper.selectList(queryWrapper);
        return Result.ok(reports);
    }
}