package com.goodtown.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodtown.mapper.PublicizeMapper;
import com.goodtown.mapper.UserMapper;
import com.goodtown.pojo.TownPromotional;
import com.goodtown.pojo.User;
import com.goodtown.utils.Result;
import org.springframework.stereotype.Service;

@Service
public class PublicizeServiceImpl extends ServiceImpl<PublicizeMapper,TownPromotional > implements PublicizeService {
    public PublicizeServiceImpl() {
        super();
    }

    public Result submit(TownPromotional data) {
        boolean res=this.save(data);
        if(res) {
            return Result.ok("提交成功");
        }
        else
        {
            return Result.ok("提交失败");
        }
    }
}
