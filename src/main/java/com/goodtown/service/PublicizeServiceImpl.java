package com.goodtown.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.mapper.PublicizeMapper;
import com.goodtown.mapper.UserMapper;
import com.goodtown.pojo.TownPromotional;
import com.goodtown.pojo.User;
import com.goodtown.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicizeServiceImpl extends ServiceImpl<PublicizeMapper,TownPromotional > implements PublicizeService {
    public PublicizeServiceImpl() {
        super();
    }

    public Result submit(TownPromotional data) {
        Long id = LoginProtectInterceptor.getUserId();
        if(id==null)
        {
            return Result.build(null,400,"请先登录");
        }
        data.setPuserid(id);
        data.setTownID(0);
        boolean res=this.save(data);
        if(res) {
            return Result.ok("提交成功");
        }
        else
        {
            return Result.ok("提交失败");
        }
    }

    @Override
    public String getAll() {
        List<TownPromotional> list = this.list();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
