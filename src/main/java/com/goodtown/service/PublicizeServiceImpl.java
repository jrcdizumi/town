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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PublicizeServiceImpl extends ServiceImpl<PublicizeMapper,TownPromotional > implements PublicizeService {
    @Autowired
    private UserService userService;

    @Autowired
    private SupportService supportService;

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
    public Result getDetail(Integer id) {
        TownPromotional res = this.getById(id);
        if(res == null)
        {
            return Result.build(null,400,"找不到该推广信息");
        }
        return Result.ok(res);
    }

    @Override
    public Result deletePromotional(Integer id,Long userId, String token) {
        Result result = userService.checkSameUser(userId, token);
        if(result.getCode()!=200)
        {
            return result;
        }
        //检查有没有助力信息，没有的才能删除，预留接口
        Result supportResult = supportService.getSupportCnt(id);
        if(supportResult.getCode() == 200) {
            Long supportCnt = (Long) supportResult.getData();
            if (supportCnt > 0) {
                return Result.build(null, 400, "该推广信息下有未处理的助力信息，不能删除");
            }
        }
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

    @Override
    public Result updatePromotional(Map<String, Object> data, String token) {
        if (token == null) {
            return Result.build(null, 400, "Token is missing");
        }
        data= data.get("data") == null ? null : (Map<String, Object>) data.get("data");
        if(data == null) {
            return Result.build(null, 400, "Data is missing");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TownPromotional promotional;
        try {
            promotional = objectMapper.convertValue(data, TownPromotional.class);
        } catch (IllegalArgumentException e) {
            return Result.build(null, 400, "Invalid data format");
        }
        if(promotional == null) {
            return Result.build(null, 400, "Data is missing");
        }
        Result result = userService.checkSameUser(promotional.getPuserid(), token);
        if(result.getCode()!=200)
        {
            return result;
        }
        Result supportResult = supportService.getSupportCnt(promotional.getPid());
        if(supportResult.getCode() == 200) {
            Long supportCnt = (Long) supportResult.getData();
            if (supportCnt > 0) {
                return Result.build(null, 400, "该推广信息下有未处理的助力信息，不能修改");
            }
        }
        promotional.setPupdatedate(LocalDateTime.now());
        TownPromotional existingPromotional = this.getById(promotional.getPid());
        if (existingPromotional == null) {
            return Result.build(null, 400, "Promotional not found");
        }
        if (promotional.getPtitle() != null) {
            existingPromotional.setPtitle(promotional.getPtitle());
        }
        if (promotional.getPdesc() != null) {
            existingPromotional.setPdesc(promotional.getPdesc());
        }
        if (promotional.getPfileList() != null) {
            existingPromotional.setPfileList(promotional.getPfileList());
        }
        if (promotional.getPstate() != null) {
            existingPromotional.setPstate(promotional.getPstate());
        }
        if (promotional.getPtypeId() != null) {
            existingPromotional.setPtypeId(promotional.getPtypeId());
        }
        if (promotional.getVideourl() != null) {
            existingPromotional.setVideourl(promotional.getVideourl());
        }
        if (promotional.getTownID() != null) {
            existingPromotional.setTownID(promotional.getTownID());
        }
        existingPromotional.setPupdatedate(LocalDateTime.now());
        boolean updateResult = this.updateById(existingPromotional);
        if (updateResult) {
            return Result.ok("更新成功");
        } else {
            return Result.build(null, 400, "更新失败");
        }
    }

}
