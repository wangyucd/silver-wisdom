package com.silver.content.controller;

import com.silver.common.api.R;
import com.silver.content.model.Category;
import com.silver.content.model.ContentItem;
import com.silver.content.model.Hotspot;
import com.silver.content.model.request.CategoryUpsertRequest;
import com.silver.content.model.request.ContentUpsertRequest;
import com.silver.content.model.request.HotspotUpsertRequest;
import com.silver.content.service.ContentFacadeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/admin")
public class AdminContentController {

    private final ContentFacadeService contentFacadeService;

    public AdminContentController(ContentFacadeService contentFacadeService) {
        this.contentFacadeService = contentFacadeService;
    }

    @GetMapping("/hotspots")
    public R<List<Hotspot>> hotspots() {
        return R.success(contentFacadeService.adminHotspots());
    }

    @PostMapping("/hotspots")
    public R<Hotspot> createHotspot(@RequestBody HotspotUpsertRequest request) {
        return R.success(contentFacadeService.saveHotspot(null, request));
    }

    @PostMapping("/hotspots/{hotspotId}")
    public R<Hotspot> updateHotspot(@PathVariable Long hotspotId, @RequestBody HotspotUpsertRequest request) {
        return R.success(contentFacadeService.saveHotspot(hotspotId, request));
    }

    @PostMapping("/categories")
    public R<Category> createCategory(@RequestBody CategoryUpsertRequest request) {
        return R.success(contentFacadeService.saveCategory(null, request));
    }

    @PostMapping("/categories/{categoryId}")
    public R<Category> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryUpsertRequest request) {
        return R.success(contentFacadeService.saveCategory(categoryId, request));
    }

    @GetMapping("/contents")
    public R<List<ContentItem>> contents() {
        return R.success(contentFacadeService.adminContents());
    }

    @PostMapping("/contents")
    public R<ContentItem> createContent(@RequestBody ContentUpsertRequest request) {
        return R.success(contentFacadeService.saveContent(null, request));
    }

    @PostMapping("/contents/{contentId}")
    public R<ContentItem> updateContent(@PathVariable Long contentId, @RequestBody ContentUpsertRequest request) {
        return R.success(contentFacadeService.saveContent(contentId, request));
    }
}
