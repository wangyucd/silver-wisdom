package com.silver.content.service;

import com.silver.content.model.CategoryEntity;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.HotspotEntity;
import com.silver.content.model.request.AiChatRequest;
import com.silver.content.model.request.AiGenerateRequest;
import com.silver.content.model.request.CategoryUpsertRequest;
import com.silver.content.model.request.ContentUpsertRequest;
import com.silver.content.model.request.HotspotUpsertRequest;
import com.silver.content.model.response.AiChatResponse;
import com.silver.content.model.response.AiGenerateTaskResponse;
import com.silver.content.model.response.ContentPageResponse;
import java.util.List;

/**
 * 内容门面服务接口。
 * 提供热点、分类、内容管理以及AI功能的统一业务入口。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface ContentFacadeService {

    /**
     * 查询当前生效的热点列表。
     * 根据当前时间在生效时间范围内的热点，按权重倒序排列。
     *
     * @return 热点实体列表
     */
    List<HotspotEntity> activeHotspots();

    /**
     * 管理端查询所有热点列表。
     * 按权重倒序排列，包含所有状态的热点。
     *
     * @return 热点实体列表
     */
    List<HotspotEntity> adminHotspots();

    /**
     * 保存热点（新增或更新）。
     * 新增时自动设置默认生效时间和状态。
     *
     * @param hotspotId 热点ID，为空表示新增
     * @param request 热点保存请求
     * @return 保存后的热点实体
     */
    HotspotEntity saveHotspot(Long hotspotId, HotspotUpsertRequest request);

    /**
     * 查询启用的分类列表。
     * 按排序值升序排列。
     *
     * @return 分类实体列表
     */
    List<CategoryEntity> categories();

    /**
     * 保存分类（新增或更新）。
     *
     * @param categoryId 分类ID，为空表示新增
     * @param request 分类保存请求
     * @return 保存后的分类实体
     */
    CategoryEntity saveCategory(Long categoryId, CategoryUpsertRequest request);

    /**
     * 分页查询内容列表。
     * 支持按分类筛选和多种排序方式。
     *
     * @param categoryId 分类ID，为空查询全部
     * @param sortBy 排序方式（HOT/LATEST）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 内容分页响应
     */
    ContentPageResponse contentPage(Long categoryId, String sortBy, int pageNum, int pageSize);

    /**
     * 管理端查询所有内容列表。
     * 按发布时间倒序排列，包含标签信息。
     *
     * @return 内容实体列表
     */
    List<ContentItemEntity> adminContents();

    /**
     * 查询内容详情。
     * 包含关联的标签信息。
     *
     * @param contentId 内容ID
     * @return 内容实体
     */
    ContentItemEntity contentDetail(Long contentId);

    /**
     * 保存内容（新增或更新）。
     * 同步处理内容标签关联关系。
     *
     * @param contentId 内容ID，为空表示新增
     * @param request 内容保存请求
     * @return 保存后的内容实体
     */
    ContentItemEntity saveContent(Long contentId, ContentUpsertRequest request);

    /**
     * AI 智能问答。
     * 基于上下文内容提供智能回答。
     *
     * @param request 问答请求
     * @return 问答响应
     */
    AiChatResponse chat(AiChatRequest request);

    /**
     * AI 内容生成。
     * 根据提示词生成结构化学习内容。
     *
     * @param request 生成请求
     * @return 生成任务响应
     */
    AiGenerateTaskResponse generate(AiGenerateRequest request);

    /**
     * 查询生成任务详情。
     *
     * @param taskId 任务ID
     * @return 生成任务详情
     */
    AiGenerateTaskResponse taskDetail(String taskId);
}
