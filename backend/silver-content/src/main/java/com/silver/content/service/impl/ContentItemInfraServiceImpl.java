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
 * 内容基础数据访问实现。
 */
@Service
public class ContentItemInfraServiceImpl extends ServiceImpl<ContentItemMapper, ContentItemEntity>
        implements IContentItemInfraService {

    /**
     * 内容标签关联 Mapper。
     */
    private final ContentTagRelMapper contentTagRelMapper;

    public ContentItemInfraServiceImpl(ContentTagRelMapper contentTagRelMapper) {
        this.contentTagRelMapper = contentTagRelMapper;
    }

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

    @Override
    public List<ContentItemEntity> attachTags(List<ContentItemEntity> contentItems) {
        return contentItems.stream().map(this::attachTags).toList();
    }
}
