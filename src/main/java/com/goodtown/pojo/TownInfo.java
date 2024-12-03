package com.goodtown.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;

/**
 * @TableName town_info
 */
@Data
public class TownInfo implements Serializable {
    @TableId
    private String townID;
    private String townName;
    private String cityID;
    private String cityName;
    private String provinceID;
    private String provinceName;

    @Version
    private Integer version;
    private Integer isDeleted;
    private static final long serialVersionUID = 1L;
}
