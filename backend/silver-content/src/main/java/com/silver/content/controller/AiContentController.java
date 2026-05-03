package com.silver.content.controller;

import com.silver.common.api.R;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.response.ContentPageResponse;
import com.silver.content.service.ai.AiContentGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AI 智能内容生成控制器。
 * 提供关键词搜索、AI 内容生成等接口。
 *
 * @author wangyu03
 * @since 2026/04/29
 */
@Slf4j
@RestController
@RequestMapping("/admin/ai/content")
@RequiredArgsConstructor
public class AiContentController {

    private final AiContentGenerateService aiContentGenerateService;

    /**
     * AI 智能内容生成。
     * 完整流程：关键词搜索 → AI 总结 → 内容存储
     *
     * @param request 生成请求，包含关键词、分类ID、标签等
     * @return 生成的内容详情
     */
    @PostMapping("/generate")
    public R<ContentItemEntity> generateContent(@RequestBody AiContentGenerateRequest request) {
        log.info("收到 AI 内容生成请求，关键词: {}", request.getKeyword());
        
        try {
            ContentItemEntity result = aiContentGenerateService.generateContent(
                    request.getKeyword(),
                    request.getCategoryId(),
                    request.getTags()
            );
            return R.success(result);
        } catch (Exception e) {
            log.error("AI 内容生成失败: {}", request.getKeyword(), e);
            return R.fail("内容生成失败：" + e.getMessage());
        }
    }

    /**
     * 仅执行搜索。
     * 返回搜索引擎结果，不进行 AI 总结和内容存储。
     *
     * @param keyword 搜索关键词
     * @return 搜索结果
     */
    @GetMapping("/search")
    public R<Map<String, String>> search(@RequestParam String keyword) {
        log.info("收到搜索请求，关键词: {}", keyword);
        
        try {
            String searchResults = aiContentGenerateService.searchOnly(keyword);
            return R.success(Map.of(
                    "keyword", keyword,
                    "results", searchResults
            ));
        } catch (Exception e) {
            log.error("搜索失败: {}", keyword, e);
            return R.fail("搜索失败：" + e.getMessage());
        }
    }

    /**
     * 基于已有搜索结果生成内容。
     * 适用于先预览搜索结果，再决定是否生成内容的场景。
     *
     * @param request 生成请求，包含关键词、已有搜索结果、分类ID、标签等
     * @return 生成的内容详情
     */
    @PostMapping("/generate-from-search")
    public R<ContentItemEntity> generateFromSearch(@RequestBody AiContentGenerateRequest request) {
        log.info("基于搜索结果生成内容，关键词: {}", request.getKeyword());
        
        if (request.getSearchResults() == null || request.getSearchResults().isBlank()) {
            return R.fail("搜索结果不能为空");
        }
        
        try {
            ContentItemEntity result = aiContentGenerateService.generateFromSearch(
                    request.getKeyword(),
                    request.getSearchResults(),
                    request.getCategoryId(),
                    request.getTags()
            );
            return R.success(result);
        } catch (Exception e) {
            log.error("基于搜索结果生成内容失败: {}", request.getKeyword(), e);
            return R.fail("内容生成失败：" + e.getMessage());
        }
    }

    /**
     * AI 内容生成请求。
     */
    @lombok.Data
    public static class AiContentGenerateRequest {
        /**
         * 内容关键词
         */
        private String keyword;
        
        /**
         * 分类ID
         */
        private Long categoryId;
        
        /**
         * 标签列表
         */
        private List<String> tags;
        
        /**
         * 已有搜索结果（用于 generate-from-search 接口）
         */
        private String searchResults;
    }
}
