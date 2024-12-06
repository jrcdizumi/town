package com.goodtown.pojo;

import java.time.LocalDateTime;
import lombok.Data;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * 
 * @TableName user
 */
@Data
public class AcceptInfo implements Serializable{
    @TableId
    private int id;          // 助力成功记录标识
    private int pid;         // 好乡镇宣传标识
    private int sid;         // 好乡镇助力标识
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdate; // 达成日期
    private int desc;        // 备注描述
    
    private static final long serialVersionUID = 1L;
}
