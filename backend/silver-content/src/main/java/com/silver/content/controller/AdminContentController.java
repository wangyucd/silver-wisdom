package com.silver.content.controller;

import com.silver.common.api.R;
import com.silver.content.model.CategoryEntity;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.HotspotEntity;
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
    public R<List<HotspotEntity>> hotspots() {
        return R.success(contentFacadeService.adminHotspots());
    }

    @PostMapping("/hotspots")
    public R<HotspotEntity> createHotspotEntity(@RequestBody HotspotUpsertRequest request) {
        return R.success(contentFacadeService.saveHotspot(null, request));
    }

    @PostMapping("/hotspots/{hotspotId}")
    public R<HotspotEntity> updateHotspotEntity(@PathVariable Long hotspotId, @RequestBody HotspotUpsertRequest request) {
        return R.success(contentFacadeService.saveHotspot(hotspotId, request));
    }

    @PostMapping("/categories")
    public R<CategoryEntity> createCategoryEntity(@RequestBody CategoryUpsertRequest request) {
        return R.success(contentFacadeService.saveCategory(null, request));
    }

    @PostMapping("/categories/{categoryId}")
    public R<CategoryEntity> updateCategoryEntity(@PathVariable Long categoryId, @RequestBody CategoryUpsertRequest request) {
        return R.success(contentFacadeService.saveCategory(categoryId, request));
    }

    @GetMapping("/contents")
    public R<List<ContentItemEntity>> contents() {
        return R.success(contentFacadeService.adminContents());
    }

    @PostMapping("/contents")
    public R<ContentItemEntity> createContent(@RequestBody ContentUpsertRequest request) {
        return R.success(contentFacadeService.saveContent(null, request));
    }

    @PostMapping("/contents/{contentId}")
    public R<ContentItemEntity> updateContent(@PathVariable Long contentId, @RequestBody ContentUpsertRequest request) {
        return R.success(contentFacadeService.saveContent(contentId, request));
    }
}
