package com.goodtown.pojo;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @TableName town_support
 */
@Data
public class TownSupport implements Serializable {
    @TableId
    private Integer sid;
    private Integer suserId;
    private Integer pid;
    private String stitle;
    private Integer sdesc;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime supportDate;
    private Integer supportState;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;
    private String sfileList;

    private static final long serialVersionUID = 1L;
}
