package com.goodtown.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.mapper.SupportMapper;
import com.goodtown.pojo.TownSupport;
import com.goodtown.service.SupportService;
import com.goodtown.service.UserService;
import com.goodtown.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SupportServiceImpl extends ServiceImpl<SupportMapper, TownSupport> implements SupportService {
    
    @Autowired
    private UserService userService;

    @Override
    public Result submitSupport(TownSupport data, String token) {
        Long userId = LoginProtectInterceptor.getUserId();
        if(userId == null) {
            return Result.build(null, 400, "请先登录");
        }
        
        data.setSuserId(userId.intValue());
        data.setSupportState(0); // 0表示未接受
        data.setSupportDate(LocalDateTime.now());
        data.setUpdateDate(LocalDateTime.now());
        
        boolean res = this.save(data);
        return res ? Result.ok("提交成功") : Result.build(null, 400, "提交失败");
    }

    @Override
    public Result updateSupport(Map<String, Object> data, String token) {
        if (token == null) {
            return Result.build(null, 400, "Token is missing");
        }
        
        data = data.get("data") == null ? null : (Map<String, Object>) data.get("data");
        if(data == null) {
            return Result.build(null, 400, "Data is missing");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TownSupport support;
        try {
            support = objectMapper.convertValue(data, TownSupport.class);
        } catch (IllegalArgumentException e) {
            return Result.build(null, 400, "Invalid data format");
        }

        Result result = userService.checkSameUser(Long.valueOf(support.getSuserId()), token);
        if(result.getCode() != 200) {
            return result;
        }

        TownSupport existingSupport = this.getById(support.getSid());
        if (existingSupport == null) {
            return Result.build(null, 400, "找不到该助力信息");
        }

        if (existingSupport.getSupportState() == 1) {
            return Result.build(null, 400, "已接受的助力信息不能修改");
        }
        
        // 如果新状态是1（接受），不允许通过更新接口设置
        if (support.getSupportState() != null && support.getSupportState() == 1) {
            return Result.build(null, 400, "不能直接将状态设置为已接受");
        }
        
        if (support.getStitle() != null) {
            existingSupport.setStitle(support.getStitle());
        }
        if (support.getSdesc() != null) {
            existingSupport.setSdesc(support.getSdesc());
        }
        if (support.getSfileList() != null) {
            existingSupport.setSfileList(support.getSfileList());
        }
        if (support.getSupportState() != null) {
            existingSupport.setSupportState(support.getSupportState());
        }
        
        existingSupport.setUpdateDate(LocalDateTime.now());
        boolean updateResult = this.updateById(existingSupport);
        
        return updateResult ? Result.ok("更新成功") : Result.build(null, 400, "更新失败");
    }

    @Override
    public Result deleteSupport(String id, Long userId, String token) {
        Result result = userService.checkSameUser(userId, token);
        if(result.getCode() != 200) {
            return result;
        }

        TownSupport support = this.getById(id);
        if (support == null) {
            return Result.build(null, 400, "找不到该助力信息");
        }

        if (!userId.equals(Long.valueOf(support.getSuserId()))) {
            return Result.build(null, 400, "无权删除他人的助力信息");
        }

        if (support.getSupportState() == 1) {
            return Result.build(null, 400, "已接受的助力信息不能删除");
        }

        support.setSupportState(3); // 设置状态为取消（逻辑删除）
        support.setUpdateDate(LocalDateTime.now());
        
        boolean updateResult = this.updateById(support);
        return updateResult ? Result.ok("删除成功") : Result.build(null, 400, "删除失败");
    }

    @Override
    public Result getDetail(String id) {
        TownSupport support = this.getById(id);
        if(support == null) {
            return Result.build(null, 400, "找不到该助力信息");
        }
        return Result.ok(support);
    }

    @Override
    public Result getSupportsByPromotionalId(String pid) {
        List<TownSupport> supports = this.lambdaQuery()
                .eq(TownSupport::getPid, pid)
                .ne(TownSupport::getSupportState, 3) // 排除已取消的记录
                .list();
        return Result.ok(supports);
    }

    @Override
    public boolean hasUnhandledSupports(String pid) {
        Long count = this.lambdaQuery()
                .eq(TownSupport::getPid, pid)
                .eq(TownSupport::getSupportState, 0)
                .count();
        return count > 0;
    }
}
