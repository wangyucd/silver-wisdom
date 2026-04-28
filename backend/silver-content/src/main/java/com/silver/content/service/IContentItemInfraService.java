package com.silver.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silver.content.model.ContentItemEntity;
import java.util.List;

/**
 * 内容项基础数据访问接口。
 * 提供内容项的基础 CRUD 及标签关联查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface IContentItemInfraService extends IService<ContentItemEntity> {

    /**
     * 加载内容标签。
     *
     * @param contentItem 内容信息
     * @return 内容信息
     */
    ContentItemEntity attachTags(ContentItemEntity contentItem);

    /**
     * 查询内容列表并携带标签。
     *
     * @param contentItems 内容列表
     * @return 内容列表
     */
    List<ContentItemEntity> attachTags(List<ContentItemEntity> contentItems);
}
