package com.goodtown.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 
 * @TableName admin
 */
@Data
public class Admin implements Serializable {
    
    @TableId
    private String username;

    private String password;
    
    private static final long serialVersionUID = 1L;
}
