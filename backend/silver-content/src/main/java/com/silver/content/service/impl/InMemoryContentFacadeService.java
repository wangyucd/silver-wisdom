package com.silver.content.service.impl;

import com.silver.common.exception.BusinessException;
import com.silver.content.converter.ContentResponseConverter;
import com.silver.content.errorcode.ContentErrorCodes;
import com.silver.content.model.AiGenerateResult;
import com.silver.content.model.AiGenerateTask;
import com.silver.content.model.Category;
import com.silver.content.model.ContentItem;
import com.silver.content.model.Hotspot;
import com.silver.content.model.request.AiChatRequest;
import com.silver.content.model.request.AiGenerateRequest;
import com.silver.content.model.request.CategoryUpsertRequest;
import com.silver.content.model.request.ContentUpsertRequest;
import com.silver.content.model.request.HotspotUpsertRequest;
import com.silver.content.model.response.AiChatResponse;
import com.silver.content.model.response.AiGenerateTaskResponse;
import com.silver.content.model.response.ContentPageResponse;
import com.silver.content.model.response.ReferenceResponse;
import com.silver.content.service.ContentFacadeService;
import com.silver.content.service.ai.AiGatewayContext;
import com.silver.content.service.ai.AiGatewayResult;
import com.silver.content.service.ai.AiGatewayService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 基于内存的内容门面服务实现。
 */
@Service
public class InMemoryContentFacadeService implements ContentFacadeService {

    /**
     * 热点存储。
     */
    private final Map<Long, Hotspot> hotspots = new LinkedHashMap<>();
    /**
     * 分类存储。
     */
    private final Map<Long, Category> categories = new LinkedHashMap<>();
    /**
     * 内容存储。
     */
    private final Map<Long, ContentItem> contentItems = new LinkedHashMap<>();
    /**
     * 生成任务存储。
     */
    private final Map<String, AiGenerateTask> generateTasks = new LinkedHashMap<>();
    /**
     * 热点ID序列。
     */
    private final AtomicLong hotspotIdSequence = new AtomicLong(10);
    /**
     * 分类ID序列。
     */
    private final AtomicLong categoryIdSequence = new AtomicLong(10);
    /**
     * 内容ID序列。
     */
    private final AtomicLong contentIdSequence = new AtomicLong(100);
    /**
     * AI 网关服务。
     */
    private final AiGatewayService aiGatewayService;
    /**
     * 内容响应转换器。
     */
    private final ContentResponseConverter contentResponseConverter;

    /**
     * 构造内容门面服务。
     *
     * @param aiGatewayService AI 网关服务
     */
    public InMemoryContentFacadeService(AiGatewayService aiGatewayService,
                                        ContentResponseConverter contentResponseConverter) {
        this.aiGatewayService = aiGatewayService;
        this.contentResponseConverter = contentResponseConverter;
        seedData();
    }

    /**
     * 查询当前有效热点。
     *
     * @return 热点列表
     */
    @Override
    public List<Hotspot> activeHotspots() {
        LocalDateTime now = LocalDateTime.now();
        return hotspots.values().stream()
                .filter(item -> item.getStartTime().isBefore(now) && item.getEndTime().isAfter(now))
                .sorted(Comparator.comparingInt(Hotspot::getWeight).reversed())
                .toList();
    }

    /**
     * 查询管理端热点列表。
     *
     * @return 热点列表
     */
    @Override
    public List<Hotspot> adminHotspots() {
        return hotspots.values().stream()
                .sorted(Comparator.comparingInt(Hotspot::getWeight).reversed())
                .toList();
    }

    /**
     * 保存热点。
     *
     * @param hotspotId 热点ID
     * @param request 热点请求
     * @return 热点信息
     */
    @Override
    public Hotspot saveHotspot(Long hotspotId, HotspotUpsertRequest request) {
        validateHotspotRequest(request);
        Hotspot hotspot = hotspotId == null ? new Hotspot() : hotspots.get(hotspotId);
        if (hotspot == null) {
            throw new BusinessException(ContentErrorCodes.HOTSPOT_NOT_FOUND);
        }
        if (hotspotId == null) {
            hotspot.setId(hotspotIdSequence.incrementAndGet());
            hotspot.setStartTime(LocalDateTime.now().minusHours(1));
            hotspot.setEndTime(LocalDateTime.now().plusDays(7));
        }
        hotspot.setTitle(request.getTitle());
        hotspot.setSummary(request.getSummary());
        hotspot.setCoverUrl(request.getCoverUrl());
        hotspot.setWeight(request.getWeight());
        hotspots.put(hotspot.getId(), hotspot);
        return hotspot;
    }

    /**
     * 查询分类列表。
     *
     * @return 分类列表
     */
    @Override
    public List<Category> categories() {
        return categories.values().stream()
                .sorted(Comparator.comparingInt(Category::getSort))
                .toList();
    }

    /**
     * 保存分类。
     *
     * @param categoryId 分类ID
     * @param request 分类请求
     * @return 分类信息
     */
    @Override
    public Category saveCategory(Long categoryId, CategoryUpsertRequest request) {
        validateCategoryRequest(request);
        Category category = categoryId == null ? new Category() : categories.get(categoryId);
        if (category == null) {
            throw new BusinessException(ContentErrorCodes.CATEGORY_NOT_FOUND);
        }
        if (categoryId == null) {
            category.setId(categoryIdSequence.incrementAndGet());
        }
        category.setName(request.getName());
        category.setIconUrl(request.getIconUrl());
        category.setCoverUrl(request.getCoverUrl());
        category.setSort(request.getSort());
        categories.put(category.getId(), category);
        return category;
    }

    /**
     * 分页查询内容。
     *
     * @param categoryId 分类ID
     * @param sortBy 排序方式
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 内容分页结果
     */
    @Override
    public ContentPageResponse contentPage(Long categoryId, String sortBy, int pageNum, int pageSize) {
        List<ContentItem> items = contentItems.values().stream()
                .filter(item -> categoryId == null || categoryId.equals(item.getCategoryId()))
                .sorted(resolveContentComparator(sortBy))
                .toList();
        int normalizedPageNum = Math.max(pageNum, 1);
        int normalizedPageSize = Math.max(pageSize, 1);
        int fromIndex = Math.min((normalizedPageNum - 1) * normalizedPageSize, items.size());
        int toIndex = Math.min(fromIndex + normalizedPageSize, items.size());
        ContentPageResponse response = new ContentPageResponse();
        response.setTotal(items.size());
        response.setList(items.subList(fromIndex, toIndex));
        return response;
    }

    /**
     * 查询管理端内容列表。
     *
     * @return 内容列表
     */
    @Override
    public List<ContentItem> adminContents() {
        return contentItems.values().stream()
                .sorted(Comparator.comparing(ContentItem::getPublishTime).reversed())
                .toList();
    }

    /**
     * 查询内容详情。
     *
     * @param contentId 内容ID
     * @return 内容详情
     */
    @Override
    public ContentItem contentDetail(Long contentId) {
        ContentItem contentItem = contentItems.get(contentId);
        if (contentItem == null) {
            throw new BusinessException(ContentErrorCodes.CONTENT_NOT_FOUND);
        }
        return contentItem;
    }

    /**
     * 保存内容。
     *
     * @param contentId 内容ID
     * @param request 内容请求
     * @return 内容详情
     */
    @Override
    public ContentItem saveContent(Long contentId, ContentUpsertRequest request) {
        validateContentRequest(request);
        ContentItem contentItem = contentId == null ? new ContentItem() : contentItems.get(contentId);
        if (contentItem == null) {
            throw new BusinessException(ContentErrorCodes.CONTENT_NOT_FOUND);
        }
        if (contentId == null) {
            contentItem.setId(contentIdSequence.incrementAndGet());
            contentItem.setPublishTime(LocalDateTime.now());
        }
        contentItem.setTitle(request.getTitle());
        contentItem.setSummary(request.getSummary());
        contentItem.setCoverUrl(request.getCoverUrl());
        contentItem.setType(request.getType());
        contentItem.setContentBody(request.getContentBody());
        contentItem.setVideoUrl(request.getVideoUrl());
        contentItem.setCategoryId(request.getCategoryId());
        contentItem.setHeatScore(request.getHeatScore());
        contentItem.getTags().clear();
        if (request.getTags() != null) {
            contentItem.getTags().addAll(request.getTags());
        }
        contentItems.put(contentItem.getId(), contentItem);
        return contentItem;
    }

    /**
     * 执行 AI 问答。
     *
     * @param request 问答请求
     * @return 问答响应
     */
    @Override
    public AiChatResponse chat(AiChatRequest request) {
        if (request == null || !StringUtils.hasText(request.getQuestion())) {
            throw new BusinessException(ContentErrorCodes.AI_CHAT_QUESTION_REQUIRED);
        }
        List<ContentItem> referencedItems = request.getContextItemIds() == null ? List.of() : request.getContextItemIds().stream()
                .map(contentItems::get)
                .filter(item -> item != null)
                .toList();

        AiGatewayContext context = new AiGatewayContext();
        context.setScene("chat");
        context.setProvider("context.openai");
        context.setPrompt(request.getQuestion());
        context.setKnowledgeSnippets(referencedItems.stream().map(ContentItem::getSummary).toList());

        AiGatewayResult result = aiGatewayService.execute(context);
        AiChatResponse response = new AiChatResponse();
        response.setAnswer(result.getAnswer());
        response.setReferences(referencedItems.stream().map(contentResponseConverter::toReferenceResponse).toList());
        return response;
    }

    /**
     * 执行 AI 生成。
     *
     * @param request 生成请求
     * @return 任务响应
     */
    @Override
    public AiGenerateTaskResponse generate(AiGenerateRequest request) {
        if (request == null || !StringUtils.hasText(request.getPrompt())) {
            throw new BusinessException(ContentErrorCodes.AI_GENERATE_PROMPT_REQUIRED);
        }
        AiGatewayContext context = new AiGatewayContext();
        context.setScene(request.getScene() == null ? "general" : request.getScene());
        context.setProvider("context.openai");
        context.setPrompt(request.getPrompt());
        context.setKnowledgeSnippets(contentItems.values().stream().limit(2).map(ContentItem::getSummary).toList());

        AiGatewayResult result = aiGatewayService.execute(context);
        String taskId = "task_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        AiGenerateResult generateResult = new AiGenerateResult();
        generateResult.setTitle(result.getTitle());
        generateResult.setSummary(result.getSummary());
        generateResult.setOutline(result.getOutline());
        generateResult.setBody(String.join("\n\n", result.getParagraphs()));

        AiGenerateTask task = new AiGenerateTask();
        task.setTaskId(taskId);
        task.setPrompt(request.getPrompt());
        task.setScene(context.getScene());
        task.setStatus("SUCCESS");
        task.setResult(generateResult);
        task.setCreatedAt(LocalDateTime.now());
        generateTasks.put(taskId, task);

        return contentResponseConverter.toTaskResponse(task);
    }

    /**
     * 查询生成任务详情。
     *
     * @param taskId 任务ID
     * @return 任务详情
     */
    @Override
    public AiGenerateTaskResponse taskDetail(String taskId) {
        AiGenerateTask task = generateTasks.get(taskId);
        if (task == null) {
            throw new BusinessException(ContentErrorCodes.AI_GENERATE_TASK_NOT_FOUND);
        }
        return contentResponseConverter.toTaskResponse(task);
    }

    /**
     * 解析内容排序器。
     *
     * @param sortBy 排序参数
     * @return 比较器
     */
    private Comparator<ContentItem> resolveContentComparator(String sortBy) {
        String normalizedSortBy = sortBy == null ? "LATEST" : sortBy.toUpperCase(Locale.ROOT);
        if ("HOT".equals(normalizedSortBy)) {
            return Comparator.comparingInt(ContentItem::getHeatScore)
                    .reversed()
                    .thenComparing(ContentItem::getPublishTime, Comparator.reverseOrder());
        }
        return Comparator.comparing(ContentItem::getPublishTime).reversed();
    }

    /**
     * 校验热点请求。
     *
     * @param request 热点请求
     */
    private void validateHotspotRequest(HotspotUpsertRequest request) {
        if (request == null || !StringUtils.hasText(request.getTitle())) {
            throw new BusinessException(ContentErrorCodes.HOTSPOT_TITLE_REQUIRED);
        }
    }

    /**
     * 校验分类请求。
     *
     * @param request 分类请求
     */
    private void validateCategoryRequest(CategoryUpsertRequest request) {
        if (request == null || !StringUtils.hasText(request.getName())) {
            throw new BusinessException(ContentErrorCodes.CATEGORY_NAME_REQUIRED);
        }
    }

    /**
     * 校验内容请求。
     *
     * @param request 内容请求
     */
    private void validateContentRequest(ContentUpsertRequest request) {
        if (request == null || !StringUtils.hasText(request.getTitle())) {
            throw new BusinessException(ContentErrorCodes.CONTENT_TITLE_REQUIRED);
        }
    }

    /**
     * 初始化演示数据。
     */
    private void seedData() {
        Category pet = new Category();
        pet.setId(1L);
        pet.setName("宠物");
        pet.setIconUrl("https://dummyimage.com/80x80/ffb86b/ffffff&text=P");
        pet.setCoverUrl("https://dummyimage.com/600x300/f6ead8/6d4c41&text=%E5%AE%A0%E7%89%A9");
        pet.setSort(1);
        categories.put(pet.getId(), pet);

        Category novel = new Category();
        novel.setId(2L);
        novel.setName("小说");
        novel.setIconUrl("https://dummyimage.com/80x80/6bb8ff/ffffff&text=N");
        novel.setCoverUrl("https://dummyimage.com/600x300/e7f1ff/345&text=%E5%B0%8F%E8%AF%B4");
        novel.setSort(2);
        categories.put(novel.getId(), novel);

        Category anime = new Category();
        anime.setId(3L);
        anime.setName("二次元");
        anime.setIconUrl("https://dummyimage.com/80x80/f28edb/ffffff&text=A");
        anime.setCoverUrl("https://dummyimage.com/600x300/ffe9fb/824f67&text=%E4%BA%8C%E6%AC%A1%E5%85%83");
        anime.setSort(3);
        categories.put(anime.getId(), anime);
        categoryIdSequence.set(3L);

        Hotspot hotspot = new Hotspot();
        hotspot.setId(1L);
        hotspot.setTitle("老年宠物护理");
        hotspot.setSummary("围绕宠物饮食、日常护理和陪伴情绪的热门专题。");
        hotspot.setCoverUrl("https://dummyimage.com/600x240/f9d8a7/5d4037&text=%E7%83%AD%E7%82%B9");
        hotspot.setWeight(99);
        hotspot.setStartTime(LocalDateTime.now().minusDays(1));
        hotspot.setEndTime(LocalDateTime.now().plusDays(5));
        hotspots.put(hotspot.getId(), hotspot);
        hotspotIdSequence.set(1L);

        ContentItem petGuide = new ContentItem();
        petGuide.setId(101L);
        petGuide.setTitle("老年人养猫入门指南");
        petGuide.setSummary("从饮食、运动到陪伴方式，帮助银发用户轻松照顾猫咪。");
        petGuide.setCoverUrl("https://dummyimage.com/480x260/f7d8b6/5f4339&text=%E5%85%BB%E7%8C%AB");
        petGuide.setType("ARTICLE");
        petGuide.setContentBody("猫咪护理的关键在于定时喂食、观察精神状态，并预留安静空间。");
        petGuide.setCategoryId(1L);
        petGuide.setHeatScore(92);
        petGuide.setPublishTime(LocalDateTime.now().minusHours(5));
        petGuide.getTags().addAll(List.of("宠物", "护理"));
        contentItems.put(petGuide.getId(), petGuide);

        ContentItem novelVideo = new ContentItem();
        novelVideo.setId(102L);
        novelVideo.setTitle("经典小说共读技巧");
        novelVideo.setSummary("适合银发学习者的共读方法，帮助更轻松进入文学世界。");
        novelVideo.setCoverUrl("https://dummyimage.com/480x260/d8e8ff/345&text=%E5%85%B1%E8%AF%BB");
        novelVideo.setType("VIDEO");
        novelVideo.setVideoUrl("https://example.com/video/novel-share.mp4");
        novelVideo.setCategoryId(2L);
        novelVideo.setHeatScore(88);
        novelVideo.setPublishTime(LocalDateTime.now().minusHours(8));
        novelVideo.getTags().addAll(List.of("小说", "共读"));
        contentItems.put(novelVideo.getId(), novelVideo);

        ContentItem animeIntro = new ContentItem();
        animeIntro.setId(103L);
        animeIntro.setTitle("二次元文化快速入门");
        animeIntro.setSummary("带家长辈轻松理解二次元常见概念与作品类型。");
        animeIntro.setCoverUrl("https://dummyimage.com/480x260/fbe0f5/824f67&text=%E4%BA%8C%E6%AC%A1%E5%85%83");
        animeIntro.setType("ARTICLE");
        animeIntro.setContentBody("可以从生活化、轻剧情的作品切入，再逐步了解人物设定与世界观。");
        animeIntro.setCategoryId(3L);
        animeIntro.setHeatScore(76);
        animeIntro.setPublishTime(LocalDateTime.now().minusDays(1));
        animeIntro.getTags().addAll(List.of("二次元", "入门"));
        contentItems.put(animeIntro.getId(), animeIntro);
        contentIdSequence.set(103L);

        AiGenerateTask task = new AiGenerateTask();
        task.setTaskId("task_seed_001");
        task.setPrompt("生成一份7天宠物陪伴计划");
        task.setScene("pet-plan");
        task.setStatus("SUCCESS");
        task.setCreatedAt(LocalDateTime.now().minusHours(2));
        AiGenerateResult result = new AiGenerateResult();
        result.setTitle("7天宠物陪伴计划");
        result.setSummary("帮助银发用户用 7 天建立稳定的宠物陪伴节奏。");
        result.setOutline("观察、互动、护理、记录");
        result.setBody("第1天观察作息\n\n第2天建立喂食节奏\n\n第3天增加互动时长");
        task.setResult(result);
        generateTasks.put(task.getTaskId(), task);
    }
}
