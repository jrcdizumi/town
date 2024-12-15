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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicizeServiceImpl extends ServiceImpl<PublicizeMapper,TownPromotional > implements PublicizeService {
    @Autowired
    private UserService userService;

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
        //添加条件：未删除的
        List<TownPromotional> list = this.lambdaQuery().eq(TownPromotional::getPstate, 0).list();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Result getDetail(String id) {
        TownPromotional res = this.getById(id);
        if(res == null)
        {
            return Result.build(null,400,"找不到该推广信息");
        }
        return Result.ok(res);
    }

    @Override
    public Result deletePromotional(String id,Long userId, String token) {
        Result result = userService.checkSameUser(userId, token);
        if(result.getCode()!=200)
        {
            return result;
        }
        //检查有没有助力信息，没有的才能删除，预留接口

        TownPromotional promotional = this.getById(id);
        if (promotional == null) {
            return Result.build(null, 400, "找不到该推广信息");
        }

        promotional.setPstate(-1);
        boolean updateResult = this.updateById(promotional);
        if (updateResult) {
            return Result.ok("删除成功");
        } else {
            return Result.build(null, 400, "删除失败");
        }

    }
}
