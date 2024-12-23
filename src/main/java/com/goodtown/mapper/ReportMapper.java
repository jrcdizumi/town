package com.goodtown.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodtown.pojo.Report;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ReportMapper extends BaseMapper<Report> {
}