package com.silver.content.converter;

import com.silver.content.model.AiGenerateTaskEntity;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.response.AiGenerateTaskResponse;
import com.silver.content.model.response.ReferenceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 内容域响应对象转换器。
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentResponseConverter {

    /**
     * 转换引用响应。
     *
     * @param contentItem 内容信息
     * @return 引用响应
     */
    @Mapping(target = "contentId", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "snippet", source = "summary")
    ReferenceResponse toReferenceResponse(ContentItemEntity contentItem);

    /**
     * 转换 AI 任务响应。
     *
     * @param task 任务信息
     * @return 任务响应
     */
    @Mapping(target = "taskId", source = "taskId")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "result", source = "result")
    @Mapping(target = "failReason", source = "failReason")
    AiGenerateTaskResponse toTaskResponse(AiGenerateTaskEntity task);
}
