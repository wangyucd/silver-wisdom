package com.silver.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用审计字段基类
 *
 * @author wangyu03
 * @since 2026/04/21 09:00
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;
    /**
     * 创建时间
     */
    @TableField("created")
    private LocalDateTime created;
    /**
     * 修改人
     */
    @TableField("modifier")
    private String modifier;
    /**
     * 更新时间
     */
    @TableField("modified")
    private LocalDateTime modified;
}
