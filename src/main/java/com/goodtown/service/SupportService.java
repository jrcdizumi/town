package com.goodtown.service;

import com.goodtown.pojo.TownSupport;
import com.goodtown.utils.Result;
import java.util.Map;

public interface SupportService {
    // 提交助力信息
    Result submitSupport(TownSupport data, String token);
    
    // 修改助力信息
    Result updateSupport(Map<String, Object> data, String token);
    
    // 删除助力信息
    Result deleteSupport(String id, Long userId, String token);
    
    // 获取助力详情
    Result getDetail(String id);
    
    // 获取某个宣传下的所有助力信息
    Result getSupportsByPromotionalId(String pid);
    
    // 检查宣传信息是否有未处理的助力
    boolean hasUnhandledSupports(String pid);
}
