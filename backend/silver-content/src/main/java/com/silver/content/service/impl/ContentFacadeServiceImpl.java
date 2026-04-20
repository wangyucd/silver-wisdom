package com.silver.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.silver.common.auth.StpAdminUtil;
import com.silver.common.auth.StpMiniappUtil;
import com.silver.common.exception.BusinessException;
import com.silver.content.converter.ContentResponseConverter;
import com.silver.content.errorcode.ContentErrorCodes;
import com.silver.content.mapper.AiGenerateResultMapper;
import com.silver.content.mapper.ContentTagRelMapper;
import com.silver.content.model.AiGenerateResultEntity;
import com.silver.content.model.AiGenerateTaskEntity;
import com.silver.content.model.CategoryEntity;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.ContentTagRelEntity;
import com.silver.content.model.HotspotEntity;
import com.silver.content.model.request.AiChatRequest;
import com.silver.content.model.request.AiGenerateRequest;
import com.silver.content.model.request.CategoryUpsertRequest;
import com.silver.content.model.request.ContentUpsertRequest;
import com.silver.content.model.request.HotspotUpsertRequest;
import com.silver.content.model.response.AiChatResponse;
import com.silver.content.model.response.AiGenerateTaskResponse;
import com.silver.content.model.response.ContentPageResponse;
import com.silver.content.service.IAiGenerateTaskInfraService;
import com.silver.content.service.ICategoryInfraService;
import com.silver.content.service.IContentItemInfraService;
import com.silver.content.service.IHotspotInfraService;
import com.silver.content.service.ContentFacadeService;
import com.silver.content.service.ai.AiGatewayContext;
import com.silver.content.service.ai.AiGatewayResult;
import com.silver.content.service.ai.AiGatewayService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 内容门面服务实现。
 */
@Service
public class ContentFacadeServiceImpl implements ContentFacadeService {

    private final IHotspotInfraService hotspotInfraService;
    private final ICategoryInfraService categoryInfraService;
    private final IContentItemInfraService contentItemInfraService;
    private final IAiGenerateTaskInfraService aiGenerateTaskInfraService;
    private final AiGenerateResultMapper aiGenerateResultMapper;
    private final ContentTagRelMapper contentTagRelMapper;
    private final AiGatewayService aiGatewayService;
    private final ContentResponseConverter contentResponseConverter;

    public ContentFacadeServiceImpl(IHotspotInfraService hotspotInfraService,
                                    ICategoryInfraService categoryInfraService,
                                    IContentItemInfraService contentItemInfraService,
                                    IAiGenerateTaskInfraService aiGenerateTaskInfraService,
                                    AiGenerateResultMapper aiGenerateResultMapper,
                                    ContentTagRelMapper contentTagRelMapper,
                                    AiGatewayService aiGatewayService,
                                    ContentResponseConverter contentResponseConverter) {
        this.hotspotInfraService = hotspotInfraService;
        this.categoryInfraService = categoryInfraService;
        this.contentItemInfraService = contentItemInfraService;
        this.aiGenerateTaskInfraService = aiGenerateTaskInfraService;
        this.aiGenerateResultMapper = aiGenerateResultMapper;
        this.contentTagRelMapper = contentTagRelMapper;
        this.aiGatewayService = aiGatewayService;
        this.contentResponseConverter = contentResponseConverter;
    }

    @Override
    public List<HotspotEntity> activeHotspots() {
        LocalDateTime now = LocalDateTime.now();
        return hotspotInfraService.lambdaQuery()
                .eq(HotspotEntity::getStatus, 1)
                .le(HotspotEntity::getStartTime, now)
                .ge(HotspotEntity::getEndTime, now)
                .orderByDesc(HotspotEntity::getWeight)
                .list();
    }

    @Override
    public List<HotspotEntity> adminHotspots() {
        return hotspotInfraService.lambdaQuery().orderByDesc(HotspotEntity::getWeight).list();
    }

    @Override
    public HotspotEntity saveHotspot(Long hotspotId, HotspotUpsertRequest request) {
        validateHotspotRequest(request);
        HotspotEntity hotspot = hotspotId == null ? new HotspotEntity() : hotspotInfraService.getById(hotspotId);
        if (hotspot == null) {
            throw new BusinessException(ContentErrorCodes.HOTSPOT_NOT_FOUND);
        }
        if (hotspotId == null) {
            hotspot.setStartTime(LocalDateTime.now().minusHours(1));
            hotspot.setEndTime(LocalDateTime.now().plusDays(7));
            hotspot.setStatus(1);
            hotspot.setCreatedBy(resolveAdminId());
        }
        hotspot.setTitle(request.getTitle());
        hotspot.setSummary(request.getSummary());
        hotspot.setCoverUrl(request.getCoverUrl());
        hotspot.setWeight(request.getWeight());
        hotspotInfraService.saveOrUpdate(hotspot);
        return hotspot;
    }

    @Override
    public List<CategoryEntity> categories() {
        return categoryInfraService.lambdaQuery()
                .eq(CategoryEntity::getStatus, 1)
                .orderByAsc(CategoryEntity::getSort)
                .list();
    }

    @Override
    public CategoryEntity saveCategory(Long categoryId, CategoryUpsertRequest request) {
        validateCategoryEntityRequest(request);
        CategoryEntity category = categoryId == null ? new CategoryEntity() : categoryInfraService.getById(categoryId);
        if (category == null) {
            throw new BusinessException(ContentErrorCodes.CATEGORY_NOT_FOUND);
        }
        if (categoryId == null) {
            category.setStatus(1);
        }
        category.setName(request.getName());
        category.setIconUrl(request.getIconUrl());
        category.setCoverUrl(request.getCoverUrl());
        category.setSort(request.getSort());
        categoryInfraService.saveOrUpdate(category);
        return category;
    }

    @Override
    public ContentPageResponse contentPage(Long categoryId, String sortBy, int pageNum, int pageSize) {
        Page<ContentItemEntity> page = new Page<>(Math.max(pageNum, 1), Math.max(pageSize, 1));
        LambdaQueryWrapper<ContentItemEntity> wrapper = new LambdaQueryWrapper<ContentItemEntity>()
                .eq(ContentItemEntity::getPublishStatus, "PUBLISHED")
                .eq(categoryId != null, ContentItemEntity::getCategoryEntityId, categoryId);
        if ("HOT".equalsIgnoreCase(sortBy)) {
            wrapper.orderByDesc(ContentItemEntity::getHeatScore).orderByDesc(ContentItemEntity::getPublishTime);
        } else {
            wrapper.orderByDesc(ContentItemEntity::getPublishTime);
        }
        Page<ContentItemEntity> resultPage = contentItemInfraService.page(page, wrapper);
        ContentPageResponse response = new ContentPageResponse();
        response.setTotal(resultPage.getTotal());
        response.setList(contentItemInfraService.attachTags(resultPage.getRecords()));
        return response;
    }

    @Override
    public List<ContentItemEntity> adminContents() {
        return contentItemInfraService.attachTags(
                contentItemInfraService.lambdaQuery().orderByDesc(ContentItemEntity::getPublishTime).list()
        );
    }

    @Override
    public ContentItemEntity contentDetail(Long contentId) {
        ContentItemEntity contentItem = contentItemInfraService.getById(contentId);
        if (contentItem == null) {
            throw new BusinessException(ContentErrorCodes.CONTENT_NOT_FOUND);
        }
        return contentItemInfraService.attachTags(contentItem);
    }

    @Override
    public ContentItemEntity saveContent(Long contentId, ContentUpsertRequest request) {
        validateContentRequest(request);
        ContentItemEntity contentItem = contentId == null ? new ContentItemEntity() : contentItemInfraService.getById(contentId);
        if (contentItem == null) {
            throw new BusinessException(ContentErrorCodes.CONTENT_NOT_FOUND);
        }
        if (contentId == null) {
            contentItem.setPublishTime(LocalDateTime.now());
            contentItem.setPublishStatus("PUBLISHED");
            contentItem.setCreatedBy(resolveAdminId());
        }
        contentItem.setTitle(request.getTitle());
        contentItem.setSummary(request.getSummary());
        contentItem.setCoverUrl(request.getCoverUrl());
        contentItem.setType(request.getType());
        contentItem.setContentBody(request.getContentBody());
        contentItem.setVideoUrl(request.getVideoUrl());
        contentItem.setCategoryEntityId(request.getCategoryEntityId());
        contentItem.setHeatScore(request.getHeatScore());
        contentItemInfraService.saveOrUpdate(contentItem);
        syncContentTags(contentItem.getId(), request.getTags());
        return contentItemInfraService.attachTags(contentItem);
    }

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        if (request == null || !StringUtils.hasText(request.getQuestion())) {
            throw new BusinessException(ContentErrorCodes.AI_CHAT_QUESTION_REQUIRED);
        }
        List<ContentItemEntity> referencedItems = request.getContextItemIds() == null ? List.of()
                : contentItemInfraService.attachTags(contentItemInfraService.listByIds(request.getContextItemIds()));
        AiGatewayContext context = new AiGatewayContext();
        context.setScene("chat");
        context.setProvider("context.openai");
        context.setPrompt(request.getQuestion());
        context.setKnowledgeSnippets(referencedItems.stream().map(ContentItemEntity::getSummary).toList());
        AiGatewayResult result = aiGatewayService.execute(context);
        AiChatResponse response = new AiChatResponse();
        response.setAnswer(result.getAnswer());
        response.setReferences(referencedItems.stream().map(contentResponseConverter::toReferenceResponse).toList());
        return response;
    }

    @Override
    public AiGenerateTaskResponse generate(AiGenerateRequest request) {
        if (request == null || !StringUtils.hasText(request.getPrompt())) {
            throw new BusinessException(ContentErrorCodes.AI_GENERATE_PROMPT_REQUIRED);
        }
        AiGatewayContext context = new AiGatewayContext();
        context.setScene(StringUtils.hasText(request.getScene()) ? request.getScene() : "general");
        context.setProvider("context.openai");
        context.setPrompt(request.getPrompt());
        context.setKnowledgeSnippets(contentItemInfraService.lambdaQuery()
                .eq(ContentItemEntity::getPublishStatus, "PUBLISHED")
                .last("limit 2")
                .list()
                .stream()
                .map(ContentItemEntity::getSummary)
                .toList());
        AiGatewayResult result = aiGatewayService.execute(context);
        String taskId = "task_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        AiGenerateTaskEntity task = new AiGenerateTaskEntity();
        task.setTaskId(taskId);
        task.setUserId(resolveMiniappUserId());
        task.setPrompt(request.getPrompt());
        task.setScene(context.getScene());
        task.setStatus("SUCCESS");
        task.setCreatedAt(LocalDateTime.now());
        aiGenerateTaskInfraService.save(task);

        AiGenerateResultEntity generateResult = new AiGenerateResultEntity();
        generateResult.setTaskId(taskId);
        generateResult.setTitle(result.getTitle());
        generateResult.setSummary(result.getSummary());
        generateResult.setOutline(result.getOutline());
        generateResult.setBody(String.join("\n\n", result.getParagraphs()));
        aiGenerateResultMapper.insert(generateResult);
        task.setResult(generateResult);
        return contentResponseConverter.toTaskResponse(task);
    }

    @Override
    public AiGenerateTaskResponse taskDetail(String taskId) {
        AiGenerateTaskEntity task = aiGenerateTaskInfraService.findByTaskId(taskId)
                .orElseThrow(() -> new BusinessException(ContentErrorCodes.AI_GENERATE_TASK_NOT_FOUND));
        return contentResponseConverter.toTaskResponse(task);
    }

    private void syncContentTags(Long contentId, List<String> tags) {
        contentTagRelMapper.delete(new LambdaQueryWrapper<ContentTagRelEntity>().eq(ContentTagRelEntity::getContentId, contentId));
        if (tags == null) {
            return;
        }
        for (String tag : tags) {
            if (!StringUtils.hasText(tag)) {
                continue;
            }
            ContentTagRelEntity rel = new ContentTagRelEntity();
            rel.setContentId(contentId);
            rel.setTag(tag.trim());
            contentTagRelMapper.insert(rel);
        }
    }

    private Long resolveAdminId() {
        return StpAdminUtil.stpLogic().isLogin() ? StpAdminUtil.stpLogic().getLoginIdAsLong() : 1L;
    }

    private Long resolveMiniappUserId() {
        return StpMiniappUtil.stpLogic().isLogin() ? StpMiniappUtil.stpLogic().getLoginIdAsLong() : 10001L;
    }

    private void validateHotspotRequest(HotspotUpsertRequest request) {
        if (request == null || !StringUtils.hasText(request.getTitle())) {
            throw new BusinessException(ContentErrorCodes.HOTSPOT_TITLE_REQUIRED);
        }
    }

    private void validateCategoryEntityRequest(CategoryUpsertRequest request) {
        if (request == null || !StringUtils.hasText(request.getName())) {
            throw new BusinessException(ContentErrorCodes.CATEGORY_NAME_REQUIRED);
        }
    }

    private void validateContentRequest(ContentUpsertRequest request) {
        if (request == null || !StringUtils.hasText(request.getTitle())) {
            throw new BusinessException(ContentErrorCodes.CONTENT_TITLE_REQUIRED);
        }
    }
}
