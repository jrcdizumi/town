package com.goodtown.service;

import com.goodtown.pojo.TownPromotional;
import com.goodtown.pojo.User;
import com.goodtown.utils.Result;

import java.util.Map;

public interface PublicizeService {
    Result submit(TownPromotional data);

    String getAll();

    Result getDetail(Integer id);

    Result deletePromotional(Integer id,Long userId, String token);

    Result updatePromotional(Map<String, Object> data, String token);
}
