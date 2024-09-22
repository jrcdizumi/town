package com.goodtown.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

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
        Result result = userService.getUserInfo(token);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(String username){
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
    public Result checkPassWord(String password){
        Result result = userService.checkPassWord(password);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        Result result = userService.checkLogin(token);
        return result;
    }
}
