package com.goodtown.service;

import com.goodtown.pojo.TownPromotional;
import com.goodtown.pojo.User;
import com.goodtown.utils.Result;

public interface PublicizeService {
    Result submit(TownPromotional data);
}
