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

/**
 * 管理端内容管理控制器。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestController
@RequestMapping("/content/admin")
public class AdminContentController {

    /**
     * 内容门面服务。
     */
    private final ContentFacadeService contentFacadeService;

    /**
     * 构造管理端内容控制器。
     *
     * @param contentFacadeService 内容门面服务
     */
    public AdminContentController(ContentFacadeService contentFacadeService) {
        this.contentFacadeService = contentFacadeService;
    }

    /**
     * 查询热点列表。
     *
     * @return 热点列表
     */
    @GetMapping("/hotspots")
    public R<List<HotspotEntity>> hotspots() {
        return R.success(contentFacadeService.adminHotspots());
    }

    /**
     * 创建热点。
     *
     * @param request 热点创建请求
     * @return 创建的热点
     */
    @PostMapping("/hotspots")
    public R<HotspotEntity> createHotspotEntity(@RequestBody HotspotUpsertRequest request) {
        return R.success(contentFacadeService.saveHotspot(null, request));
    }

    /**
     * 更新热点。
     *
     * @param hotspotId 热点ID
     * @param request 热点更新请求
     * @return 更新后的热点
     */
    @PostMapping("/hotspots/{hotspotId}")
    public R<HotspotEntity> updateHotspotEntity(@PathVariable Long hotspotId, @RequestBody HotspotUpsertRequest request) {
        return R.success(contentFacadeService.saveHotspot(hotspotId, request));
    }

    /**
     * 创建分类。
     *
     * @param request 分类创建请求
     * @return 创建的分类
     */
    @PostMapping("/categories")
    public R<CategoryEntity> createCategoryEntity(@RequestBody CategoryUpsertRequest request) {
        return R.success(contentFacadeService.saveCategory(null, request));
    }

    /**
     * 更新分类。
     *
     * @param categoryId 分类ID
     * @param request 分类更新请求
     * @return 更新后的分类
     */
    @PostMapping("/categories/{categoryId}")
    public R<CategoryEntity> updateCategoryEntity(@PathVariable Long categoryId, @RequestBody CategoryUpsertRequest request) {
        return R.success(contentFacadeService.saveCategory(categoryId, request));
    }

    /**
     * 查询内容列表。
     *
     * @return 内容列表
     */
    @GetMapping("/contents")
    public R<List<ContentItemEntity>> contents() {
        return R.success(contentFacadeService.adminContents());
    }

    /**
     * 创建内容。
     *
     * @param request 内容创建请求
     * @return 创建的内容
     */
    @PostMapping("/contents")
    public R<ContentItemEntity> createContent(@RequestBody ContentUpsertRequest request) {
        return R.success(contentFacadeService.saveContent(null, request));
    }

    /**
     * 更新内容。
     *
     * @param contentId 内容ID
     * @param request 内容更新请求
     * @return 更新后的内容
     */
    @PostMapping("/contents/{contentId}")
    public R<ContentItemEntity> updateContent(@PathVariable Long contentId, @RequestBody ContentUpsertRequest request) {
        return R.success(contentFacadeService.saveContent(contentId, request));
    }
}
