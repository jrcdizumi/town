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