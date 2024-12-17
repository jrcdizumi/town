package com.goodtown.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.goodtown.interceptors.LoginProtectInterceptor;
import com.goodtown.pojo.User;
import com.goodtown.service.UserService;
import com.goodtown.utils.Result;

@RestController
@RequestMapping("/user")
@CrossOrigin
@SuppressWarnings("rawtypes")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        System.out.println("result = " + result);
        return result;
    }

    @GetMapping("getUserInfo")
    public Result userInfo(@RequestHeader String token){
        Long userId = LoginProtectInterceptor.getUserId();
        Result result = userService.getUserInfo(userId);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(@RequestBody String username){
        Result result = userService.checkUserName(username);
        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }

    @PostMapping("updateUserInfo")
    public Result updateUserInfo(@RequestBody User user){
        Result result = userService.updateUserInfo(user);
        return result;
    }

    @PostMapping("checkPassWord")
    public Result checkPassWord(@RequestBody String password){
        Result result = userService.checkPassWord(password);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        Result result = userService.checkLogin(token);
        return result;
    }

    @GetMapping("checkSameUser")
    public Result checkSameUser(@RequestParam Long userId, @RequestHeader(value = "token", required = false) String token){
        return userService.checkSameUser(userId,token);
    }

    @GetMapping("getUserName")
    public Result getUserName(@RequestParam Long userId){
        return userService.getUserName(userId);
    }
}
