package com.silver.content.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silver.common.model.BaseEntity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 热点实体。
 * 存储平台首页轮播展示的热点推荐内容。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@TableName("hotspot")
@Getter
@Setter
@NoArgsConstructor
public class HotspotEntity extends BaseEntity {

    /**
     * 热点ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 热点标题
     */
    private String title;
    /**
     * 热点摘要
     */
    private String summary;
    /**
     * 热点封面
     */
    private String coverUrl;
    /**
     * 热点权重
     */
    private int weight;
    /**
     * 上线时间
     */
    private LocalDateTime startTime;
    /**
     * 下线时间
     */
    private LocalDateTime endTime;
    /**
     * 热点状态
     */
    private Integer status;
}
