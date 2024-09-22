package com.goodtown.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodtown.pojo.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @author lgz
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-09-20 15:20:22
* @Entity com.goodtown.pojo.User
*/
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

}
