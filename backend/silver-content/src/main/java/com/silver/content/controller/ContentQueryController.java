package com.silver.content.controller;

import com.silver.common.api.R;
import com.silver.content.model.Category;
import com.silver.content.model.ContentItem;
import com.silver.content.model.Hotspot;
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

@RestController
@RequestMapping("/content")
public class ContentQueryController {

    private final ContentFacadeService contentFacadeService;

    public ContentQueryController(ContentFacadeService contentFacadeService) {
        this.contentFacadeService = contentFacadeService;
    }

    @GetMapping("/hotspots/active")
    public R<List<Hotspot>> activeHotspots() {
        return R.success(contentFacadeService.activeHotspots());
    }

    @GetMapping("/categories")
    public R<List<Category>> categories() {
        return R.success(contentFacadeService.categories());
    }

    @GetMapping("/items")
    public R<ContentPageResponse> contentPage(@RequestParam(required = false) Long categoryId,
                                              @RequestParam(defaultValue = "LATEST") String sortBy,
                                              @RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        return R.success(contentFacadeService.contentPage(categoryId, sortBy, pageNum, pageSize));
    }

    @GetMapping("/items/{contentId}")
    public R<ContentItem> contentDetail(@PathVariable Long contentId) {
        return R.success(contentFacadeService.contentDetail(contentId));
    }

    @PostMapping("/ai/chat")
    public R<AiChatResponse> chat(@RequestBody AiChatRequest request) {
        return R.success(contentFacadeService.chat(request));
    }

    @PostMapping("/ai/generate")
    public R<AiGenerateTaskResponse> generate(@RequestBody AiGenerateRequest request) {
        return R.success(contentFacadeService.generate(request));
    }

    @GetMapping("/ai/generate/{taskId}")
    public R<AiGenerateTaskResponse> taskDetail(@PathVariable String taskId) {
        return R.success(contentFacadeService.taskDetail(taskId));
    }
}
