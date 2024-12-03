package com.goodtown.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;

/**
 * 
 * @TableName promotional_type
 */
@Data
public class PromotionalType implements Serializable {
    @TableId
    private Integer id;         // 好乡镇宣传类型标识

    private String typename;    // 好乡镇宣传类型名称

    @Version
    private Integer version;
    private Integer isDeleted;
    private static final long serialVersionUID = 1L;
}
