package com.goodtown.service;

import com.goodtown.pojo.User;
import com.goodtown.utils.Result;

@SuppressWarnings("rawtypes")
public interface UserService {

    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);

    Result updateUserInfo(User user);

    Result checkPassWord(String password);
}
