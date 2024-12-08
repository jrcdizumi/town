package com.goodtown.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @TableName town_promotional
 */
@Data
public class TownPromotional implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer pid;
    private String ptitle;
    private Integer ptypeId;
    private Long puserid;
    //指定列名就是townID
    @TableField(value = "townID")
    private Integer townID;
    private String pdesc;
    private String pfileList;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime pbegindate;
    private Integer pstate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime pupdatedate;

    private String videourl;

    private static final long serialVersionUID = 1L;
}
