package com.goodtown.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.mapper.SupportMapper;
import com.goodtown.pojo.TownPromotional;
import com.goodtown.pojo.TownSupport;
import com.goodtown.utils.JwtHelper;
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
    
    @Autowired
    private PublicizeService publicizeService;  // 添加这个注入

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result submitSupport(TownSupport data, String token) {
        Long userId = LoginProtectInterceptor.getUserId();
        if(userId == null) {
            return Result.build(null, 400, "Please login first");
        }
        
        data.setSuserId(userId.intValue());
        data.setSupportState(0); // 0表示未接受
        data.setSupportDate(LocalDateTime.now());
        data.setUpdateDate(LocalDateTime.now());
        
        boolean res = this.save(data);
        return res ? Result.ok("Submit successful") : Result.build(null, 400, "Submit failed");
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
            return Result.build(null, 400, "Support not found");
        }
        if(existingSupport.getSupportState() == 3) {
            return Result.build(null, 400, "This support has been deleted");
        }
        if (existingSupport.getSupportState() == 1) {
            return Result.build(null, 400, "Cannot update a support that has been accepted");
        }
        
        // 如果新状态是1（接受），不允许通过更新接口设置
        if (support.getSupportState() != null && support.getSupportState() == 1) {
            return Result.build(null, 400, "Cannot update support state to accepted");
        }
        
        if (support.getSupportState() != null && support.getSupportState() == 2) {
            return Result.build(null, 400, "Cannot update support state to rejected");
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
        if (support.getVideourl() != null) {
            existingSupport.setVideourl(support.getVideourl());
        }
        
        existingSupport.setUpdateDate(LocalDateTime.now());
        boolean updateResult = this.updateById(existingSupport);
        
        return updateResult ? Result.ok("更新成功") : Result.build(null, 400, "更新失败");
    }

    @Override
    public Result deleteSupport(Integer id, Long userId) {

        TownSupport support = this.getById(id);
        if(userId == null) {
            return Result.build(null, 400, "Please login first");
        }
        if (support == null) {
            return Result.build(null, 400, "Support not found");
        }
        if(support.getSuserId() == null) {
            return Result.build(null, 400, "Support not found");
        }
        if(!userId.equals(Long.valueOf(support.getSuserId()))) {
            return Result.build(null, 400, "No permission to delete other user's support");
        }
        if(support.getSupportState() == 3) {
            return Result.build(null, 400, "This support has been deleted");
        }
        if (!userId.equals(Long.valueOf(support.getSuserId()))) {
            return Result.build(null, 400, "No permission to delete other user's support");
        }

        if (support.getSupportState() == 1) {
            return Result.build(null, 400, "Cannot delete an accepted support");
        }

        support.setSupportState(3); // 设置状态为取消（逻辑删除）
        support.setUpdateDate(LocalDateTime.now());
        
        boolean updateResult = this.updateById(support);
        return updateResult ? Result.ok("Delete successful") : Result.build(null, 400, "Delete failed");
    }

    @Override
    public Result getDetail(Integer id) {
        TownSupport support = this.getById(id);
        if(support == null) {
            return Result.build(null, 400, "Support not found");
        }
        if(support.getSupportState() == 3) {
            return Result.build(null, 400, "This support has been deleted");
        }
        return Result.ok(support);
    }

    @Override
    public Result getSupportsByPromotionalId(Integer pid, String token) {
        Long userId = jwtHelper.getUserId(token);
        List<TownSupport> supports;
        if (userId == null) {
            supports = this.lambdaQuery()
                .eq(TownSupport::getPid, pid)
                .eq(TownSupport::getSupportState, 1)
                .list();
            return Result.ok(supports);
        }
        //如果userid等于pid对应的userid，返回所有记录
        TownPromotional promotional = (TownPromotional) publicizeService.getDetail(pid).getData();
        if (userId.equals(promotional.getPuserid())) {
            supports = this.lambdaQuery()
                .eq(TownSupport::getPid, pid)
                .ne(TownSupport::getSupportState, 3)
                .ne(TownSupport::getSupportState, 2)
                .list();
            return Result.ok(supports);
        }
        //如果userid不等于pid对应的userid，返回已接受的记录
        supports = this.lambdaQuery()
            .eq(TownSupport::getPid, pid)
            .eq(TownSupport::getSupportState, 1)
            .list();
        return Result.ok(supports);
    }

    @Override
    public boolean hasSupports(Integer pid) {
        Long count = this.lambdaQuery()
                .eq(TownSupport::getPid, pid)
                .ne(TownSupport::getSupportState, 3)
                .count();
        return count > 0;
    }

    @Override
    public Result handleSupport(Integer supportId, Integer action, Long userId) {
        if (action != 1 && action != 2) {
            return Result.build(null, 400, "Invalid action type");
        }

        TownSupport support = this.getById(supportId);
        if (support == null) {
            return Result.build(null, 400, "Support not found");
        }
        if(support.getSupportState() == 3) {
            return Result.build(null, 400, "This support has been deleted");
        }
        // 获取宣传信息
        Result promotionalResult = publicizeService.getDetail(support.getPid());
        if (promotionalResult.getCode() != 200) {
            return Result.build(null, 400, "Promotional not found");
        }
        TownPromotional promotional = (TownPromotional) promotionalResult.getData();

        // 检查当前用户是否为宣传信息的发布者
        if (!userId.equals(promotional.getPuserid())) {
            return Result.build(null, 400, "Only promotional owner can handle support requests");
        }

        // 检查助力信息当前状态
        if (support.getSupportState() != 0) {
            return Result.build(null, 400, "This support has already been handled");
        }

        // 更新助力信息状态
        support.setSupportState(action);  // 1-接受，2-拒绝
        support.setUpdateDate(LocalDateTime.now());
        
        boolean updateResult = this.updateById(support);
        if (!updateResult) {
            return Result.build(null, 400, "Operation failed");
        }

        String message = action == 1 ? "Support request accepted" : "Support request rejected";
        return Result.ok(message);
    }

    @Override
    public Result getMySupportsList(Long uid) {
        List<TownSupport> supports = this.lambdaQuery()
                .eq(TownSupport::getSuserId, uid)
                .ne(TownSupport::getSupportState, 3) // 排除已取消的记录
                .list();
        return Result.ok(supports);
    }

    @Override
    public Result checkPromotionUserMatch(Integer supportId, String token) {
        Long userId = LoginProtectInterceptor.getUserId();
        if (userId == null) {
            return Result.build(null, 400, "请先登录");
        }

        TownSupport support = this.getById(supportId);
        if (support == null) {
            return Result.build(null, 400, "助力信息不存在");
        }

        Result promotionalResult = publicizeService.getDetail(support.getPid());
        if (promotionalResult.getCode() != 200) {
            return Result.build(null, 400, "宣传信息不存在");
        }

        TownPromotional promotional = (TownPromotional) promotionalResult.getData();
        if (!userId.equals(promotional.getPuserid())) {
            return Result.build(null, 400, "无权限操作");
        }

        return Result.ok("用户匹配成功");
    }

    @Override
    public Result getSupportCnt(Integer pid) {
        Long count = this.lambdaQuery()
                .eq(TownSupport::getPid, pid)
                .ne(TownSupport::getSupportState, 3) // 排除已取消的记录
                .count();
        return Result.ok(count);
    }
}
