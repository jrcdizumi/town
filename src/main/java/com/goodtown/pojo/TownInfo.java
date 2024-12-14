package com.goodtown.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @TableName town_info
 */
@Data
public class TownInfo implements Serializable {

    @TableField(value = "townID")
    private String townID;

    @TableField(value = "townName")
    private String townName;
    @TableField(value = "cityName")
    private String cityName;
    @TableField(value = "provinceName")
    private String provinceName;

    private static final long serialVersionUID = 1L;
}
