package com.silver.content.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silver.content.mapper.ContentItemMapper;
import com.silver.content.mapper.ContentTagRelMapper;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.ContentTagRelEntity;
import com.silver.content.service.IContentItemInfraService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * 内容项基础数据访问实现。
 * 提供内容项的 CRUD 操作及标签关联查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class ContentItemInfraServiceImpl extends ServiceImpl<ContentItemMapper, ContentItemEntity>
        implements IContentItemInfraService {

    /**
     * 内容标签关联 Mapper
     */
    private final ContentTagRelMapper contentTagRelMapper;

    /**
     * 构造内容项基础服务。
     *
     * @param contentTagRelMapper 内容标签关联 Mapper
     */
    public ContentItemInfraServiceImpl(ContentTagRelMapper contentTagRelMapper) {
        this.contentTagRelMapper = contentTagRelMapper;
    }

    /**
     * 为单个内容项加载标签。
     *
     * @param contentItem 内容项
     * @return 携带标签的内容项
     */
    @Override
    public ContentItemEntity attachTags(ContentItemEntity contentItem) {
        if (contentItem == null) {
            return null;
        }
        contentItem.getTags().clear();
        contentItem.getTags().addAll(contentTagRelMapper.selectList(
                Wrappers.<ContentTagRelEntity>lambdaQuery().eq(ContentTagRelEntity::getContentId, contentItem.getId())
        ).stream().map(ContentTagRelEntity::getTag).collect(Collectors.toList()));
        return contentItem;
    }

    /**
     * 为内容项列表批量加载标签。
     *
     * @param contentItems 内容项列表
     * @return 携带标签的内容项列表
     */
    @Override
    public List<ContentItemEntity> attachTags(List<ContentItemEntity> contentItems) {
        return contentItems.stream().map(this::attachTags).toList();
    }
}
