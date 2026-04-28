package com.silver.content.controller;

import com.silver.common.api.R;
import com.silver.content.model.CategoryEntity;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.HotspotEntity;
import com.silver.content.model.request.AiChatRequest;
import com.silver.content.model.request.AiGenerateRequest;
import com.silver.content.model.response.AiChatResponse;
import com.silver.content.model.response.AiGenerateTaskResponse;
import com.silver.content.model.response.ContentPageResponse;
import com.silver.content.service.ContentFacadeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内容查询控制器。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestController
@RequestMapping("/content")
public class ContentQueryController {

    /**
     * 内容门面服务。
     */
    private final ContentFacadeService contentFacadeService;

    /**
     * 构造内容查询控制器。
     *
     * @param contentFacadeService 内容门面服务
     */
    public ContentQueryController(ContentFacadeService contentFacadeService) {
        this.contentFacadeService = contentFacadeService;
    }

    /**
     * 查询当前生效的热点列表。
     *
     * @return 热点列表
     */
    @GetMapping("/hotspots/active")
    public R<List<HotspotEntity>> activeHotspots() {
        return R.success(contentFacadeService.activeHotspots());
    }

    /**
     * 查询分类列表。
     *
     * @return 分类列表
     */
    @GetMapping("/categories")
    public R<List<CategoryEntity>> categories() {
        return R.success(contentFacadeService.categories());
    }

    /**
     * 分页查询内容列表。
     *
     * @param categoryId 分类ID
     * @param sortBy 排序方式
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 内容分页数据
     */
    @GetMapping("/items")
    public R<ContentPageResponse> contentPage(@RequestParam(required = false) Long categoryId,
                                              @RequestParam(defaultValue = "LATEST") String sortBy,
                                              @RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        return R.success(contentFacadeService.contentPage(categoryId, sortBy, pageNum, pageSize));
    }

    /**
     * 查询内容详情。
     *
     * @param contentId 内容ID
     * @return 内容详情
     */
    @GetMapping("/items/{contentId}")
    public R<ContentItemEntity> contentDetail(@PathVariable Long contentId) {
        return R.success(contentFacadeService.contentDetail(contentId));
    }

    /**
     * AI 智能问答。
     *
     * @param request 问答请求
     * @return 问答响应
     */
    @PostMapping("/ai/chat")
    public R<AiChatResponse> chat(@RequestBody AiChatRequest request) {
        return R.success(contentFacadeService.chat(request));
    }

    /**
     * AI 内容生成。
     *
     * @param request 生成请求
     * @return 生成任务响应
     */
    @PostMapping("/ai/generate")
    public R<AiGenerateTaskResponse> generate(@RequestBody AiGenerateRequest request) {
        return R.success(contentFacadeService.generate(request));
    }

    /**
     * 查询生成任务详情。
     *
     * @param taskId 任务ID
     * @return 生成任务详情
     */
    @GetMapping("/ai/generate/{taskId}")
    public R<AiGenerateTaskResponse> taskDetail(@PathVariable String taskId) {
        return R.success(contentFacadeService.taskDetail(taskId));
    }
}
