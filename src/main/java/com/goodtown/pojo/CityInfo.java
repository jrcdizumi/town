package com.goodtown.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 
 * @TableName city_info
 */
@Data
public class CityInfo implements Serializable {
    @TableId
    private Integer cityID;        // 城市标识

    private String cityName;       // 城市名称
    private Integer provinceID;    // 省标识
    private String provinceName;   // ��名称

    private static final long serialVersionUID = 1L;
}
