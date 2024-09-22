package com.goodtown.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodtown.mapper.UserMapper;
import com.goodtown.pojo.User;
import com.goodtown.utils.JwtHelper;
import com.goodtown.utils.MD5Util;
import com.goodtown.utils.Result;
import com.goodtown.utils.ResultCodeEnum;

@SuppressWarnings("rawtypes")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private  UserMapper userMapper;

    /**
     * 登录业务实现
     * @param user
     * @return result封装
     */
    @SuppressWarnings("unchecked")
    @Override
    public Result login(User user) {

        //根据账号查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(queryWrapper);

        //账号判断
        if (loginUser == null) {
            //账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //判断密码
        if (!StringUtils.isEmpty(user.getPassword())
                && loginUser.getPassword().equals(MD5Util.encrypt(user.getPassword())))
        {
           //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getId()));

            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }

        //密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
    * 查询用户数据
    * @param token
    * @return result封装
    */
    @SuppressWarnings("unchecked")
    @Override
    public Result getUserInfo(String token) {

        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        //2.获取token对应的用户
        int userId = jwtHelper.getUserId(token).intValue();

        //3.查询数据
        User user = userMapper.selectById(userId);

        if (user != null) {
            user.setPassword(null);
            Map data = new HashMap();
            data.put("loginUser",user);
            return Result.ok(data);
        }

        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    /**
    * 检查账号是否可以注册
    *
    * @param username 账号信息
    * @return
    */
    @Override
    public Result checkUserName(String username) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(queryWrapper);

        if (user != null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        return Result.ok(null);
    }

    @Override
    public Result checkPassWord(String password) {
        // 检查密码长度是否不少于 6 位
        if (password.length() < 6) {
            return Result.build(null, ResultCodeEnum.PASSWORD_TOO_SHORT);
        }
    
        // 检查密码是否包含至少两个数字
        int digitCount = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        if (digitCount < 2) {
            return Result.build(null, ResultCodeEnum.PASSWORD_TOO_FEW_DIGITS);
        }
    
        // 检查密码是否不能全为大写或小写
        if (password.equals(password.toLowerCase()) || password.equals(password.toUpperCase())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_CASE_REQUIREMENT);
        }
        return Result.ok(null);
    }

    @Override
    public Result regist(User user) {
        // 获取用户输入的密码
        String password = user.getPassword();
    
        // 检查密码长度是否不少于 6 位
        if (password.length() < 6) {
            return Result.build(null, ResultCodeEnum.PASSWORD_TOO_SHORT);
        }
    
        // 检查密码是否包含至少两个数字
        int digitCount = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        if (digitCount < 2) {
            return Result.build(null, ResultCodeEnum.PASSWORD_TOO_FEW_DIGITS);
        }
    
        // 检查密码是否不能全为大写或小写
        if (password.equals(password.toLowerCase()) || password.equals(password.toUpperCase())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_CASE_REQUIREMENT);
        }
    
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);
    
        if (count > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
    
        user.setPassword(MD5Util.encrypt(password));
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        return Result.ok(null);
    }

    @Override
    public Result updateUserInfo(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User user1 = userMapper.selectOne(queryWrapper);

        if (user1 == null){
            return Result.build(null,ResultCodeEnum.USERNAME_ERROR);
        }

        user.setId(user1.getId());
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        int rows = userMapper.updateById(user);
        System.out.println("rows = " + rows);
        return Result.ok(null);
    }
}
