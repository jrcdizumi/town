package com.goodtown.service;

import com.goodtown.pojo.TownPromotional;
import com.goodtown.pojo.User;
import com.goodtown.utils.Result;

public interface PublicizeService {
    Result submit(TownPromotional data);

    String getAll();

    Result getDetail(String id);

    Result deletePromotional(String id,Long userId, String token);
}
