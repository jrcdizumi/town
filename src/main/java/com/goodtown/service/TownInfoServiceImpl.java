package com.goodtown.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodtown.mapper.PublicizeMapper;
import com.goodtown.mapper.TownInfoMapper;
import com.goodtown.pojo.TownInfo;
import com.goodtown.pojo.TownPromotional;
import org.springframework.stereotype.Service;

@Service
public class TownInfoServiceImpl extends ServiceImpl<TownInfoMapper, TownInfo> implements TownInfoService{
}
