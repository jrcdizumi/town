package com.goodtown.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;

public class Admin implements Serializable {
    
    @TableId
    private String username;

    private String password;
}
