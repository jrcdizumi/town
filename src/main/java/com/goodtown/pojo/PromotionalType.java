package com.goodtown.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 
 * @TableName promotional_type
 */
@Data
public class PromotionalType implements Serializable {
    @TableId
    private Integer id;         // 好乡镇宣传类型标识

    private String typename;    // 好乡镇宣传类型名称

    private static final long serialVersionUID = 1L;
}
