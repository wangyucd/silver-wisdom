package com.silver.content.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 热点实体。
 */
@TableName("hotspot")
@Getter
@Setter
@NoArgsConstructor
public class HotspotEntity {

    /** 热点ID。 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 热点标题。 */
    private String title;
    /** 热点摘要。 */
    private String summary;
    /** 热点封面。 */
    private String coverUrl;
    /** 热点权重。 */
    private int weight;
    /** 上线时间。 */
    private LocalDateTime startTime;
    /** 下线时间。 */
    private LocalDateTime endTime;
    /** 热点状态。 */
    private Integer status;
    /** 创建管理员ID。 */
    private Long createdBy;

}
