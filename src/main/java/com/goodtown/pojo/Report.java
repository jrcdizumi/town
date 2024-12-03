package com.goodtown.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 
 * @TableName report
 */
@Data
public class Report implements Serializable {
    @TableId
    private String monthID;    // 统计月份

    private Integer ptypeId;   // 好乡镇宣传类型标识
    private String townID;     // 好乡镇标识
    private Integer puserNum;  // 月累计宣传用户数
    private Integer suserNum;  // 月累计助力用户数
    
    private static final long serialVersionUID = 1L;
}
