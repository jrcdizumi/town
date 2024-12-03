package com.goodtown.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 
 * @TableName user
 */
@Data
public class User implements Serializable{

    //用户信息类：用户标识、用户名、登录密码、用户类型（系统管理员/普通用户）、用户姓名、证件类型、证件号码、手机号码(11 位数字)、用户简介、注册时间、修改时间
    @TableId
    private Integer id;
    private String uname;
    private String bpwd;
    private String bname;
    private String ctype;
    private String idno;
    private String phoneno;
    @TableField("`desc`")
    private String description;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime rdate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime udate;
    private String userlvl;

    private static final long serialVersionUID = 1L;
}