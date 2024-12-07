package com.goodtown.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodtown.mapper.PromotionalTypeMapper;
import com.goodtown.pojo.PromotionalType;
import com.goodtown.utils.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PromotionalTypeServiceImpl extends ServiceImpl<PromotionalTypeMapper, PromotionalType> {
    public PromotionalTypeServiceImpl() {
        super();
    }

    public HashMap<String,Integer> getTypeList()
    {
        HashMap<String,Integer> res=new HashMap<>();
        for(PromotionalType type:this.list())
        {
            res.put(type.getTypename(),type.getId());
        }
        return res;
    }
}
