package com.goodtown.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

import java.time.LocalDateTime;

@SuppressWarnings("rawtypes")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录业务实现
     * @param user
     * @return result封装
     */
    @Override
    public Result login(User user) {
        // 从 Redis 中获取用户信息
        User loginUser = (User) redisTemplate.opsForValue().get("user:" + user.getUsername());

        // 如果 Redis 中没有缓存用户信息，则从数据库中查询
        if (loginUser == null) {
            // 根据账号查询
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername, user.getUsername());
            loginUser = userMapper.selectOne(queryWrapper);

            // 账号判断
            if (loginUser == null) {
                // 账号错误
                return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
            }

            // 将用户信息缓存到 Redis 中
            redisTemplate.opsForValue().set("user:" + user.getUsername(), loginUser);
        }

        // 判断密码
        if (!StringUtils.isEmpty(user.getPassword())
                && loginUser.getPassword().equals(MD5Util.encrypt(user.getPassword()))) {
            // 账号密码正确
            // 根据用户唯一标识生成 token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getId()));

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            return Result.ok(data);
        }

        // 密码错误
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
    * 查询用户数据
    * @param token
    * @return result封装
    */
    @Override
    public Result getUserInfo(String username) {
        // 从 Redis 中获取用户信息
        User user = (User) redisTemplate.opsForValue().get("user:" + username);

        // 如果 Redis 中没有缓存用户信息，则从数据库中查询
        if (user == null) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername, username);
            user = userMapper.selectOne(queryWrapper);

            // 如果数据库中也没有用户信息，则返回错误
            if (user == null) {
                return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
            }

            // 将用户信息缓存到 Redis 中
            redisTemplate.opsForValue().set("user:" + username, user);
        }

        // 返回用户信息
        return Result.ok(user);
    }

    /**
    * 检查账号是否可以注册
    *
    * @param username 账号信息
    * @return
    */
    @Override
    public Result checkUserName(String username) {
        // 从 Redis 中获取用户信息
        User user = (User) redisTemplate.opsForValue().get("user:" + username);

        // 如果 Redis 中没有缓存用户信息，则从数据库中查询
        if (user == null) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername, username);
            user = userMapper.selectOne(queryWrapper);

            // 如果数据库中也没有用户信息，则说明用户名可以注册
            if (user == null) {
                return Result.ok(null);
            }

            // 将用户信息缓存到 Redis 中
            redisTemplate.opsForValue().set("user:" + username, user);
        }

        // 如果用户信息存在，则说明用户名已被使用
        return Result.build(null, ResultCodeEnum.USERNAME_USED);
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
        
        // 将用户信息缓存到 Redis 中
        redisTemplate.opsForValue().set("user:" + user.getUsername(), user);
        return Result.ok(null);
    }

    @Override
    public Result updateUserInfo(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User user1 = userMapper.selectOne(queryWrapper);

        if (user1 == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        user.setId(user1.getId());
        user.setPassword(MD5Util.encrypt(user.getPassword()));     

        int rows = userMapper.updateById(user);
        System.out.println("rows = " + rows); 
               
        // 更新 Redis 缓存中的用户信息
        user = userMapper.selectById(user.getId());
        redisTemplate.opsForValue().set("user:" + user.getUsername(), user);
        return Result.ok(null);
    }

    @Override
    public Result checkLogin(String token) {
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)) {
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}
