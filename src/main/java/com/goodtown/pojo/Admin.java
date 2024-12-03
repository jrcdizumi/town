package com.goodtown.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;

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
    
    @Version
    private Integer version;
    private Integer isDeleted;   
    private static final long serialVersionUID = 1L;
}
