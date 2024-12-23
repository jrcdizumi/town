package com.goodtown.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodtown.pojo.Admin;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @author hdk
* @description 针对表【Admin】的数据库操作Mapper
* @createDate 2024-12-23 15:20:22
* @Entity com.goodtown.pojo.Admin
*/
@Mapper
@Component
public interface AdminMapper extends BaseMapper<Admin> {

}
