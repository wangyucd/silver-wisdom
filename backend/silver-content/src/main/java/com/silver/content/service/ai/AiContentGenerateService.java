package com.silver.content.service.ai;

import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.request.ContentUpsertRequest;
import com.silver.content.service.ContentFacadeService;
import com.silver.content.service.ai.search.DuckDuckGoSearchService;
import com.silver.content.service.ai.summarize.AiSummarizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI 智能内容生成服务。
 * 完整的生成流程：关键词 → 搜索引擎搜索 → AI 总结 → 内容存储
 *
 * @author wangyu03
 * @since 2026/04/29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiContentGenerateService {

    private final DuckDuckGoSearchService searchService;
    private final AiSummarizeService summarizeService;
    private final ContentFacadeService contentFacadeService;

    /**
     * 根据关键词生成内容。
     * 完整流程：搜索 → AI 总结 → 存储
     *
     * @param keyword 内容关键词
     * @param categoryId 分类ID（可选）
     * @param tags 标签列表（可选）
     * @return 生成的内容实体
     */
    public ContentItemEntity generateContent(String keyword, Long categoryId, List<String> tags) {
        log.info("开始 AI 智能内容生成，关键词: {}", keyword);

        // Step 1: 搜索引擎搜索
        log.info("Step 1: 执行 DuckDuckGo 搜索...");
        String searchResults = searchService.search(keyword);
        if (searchResults == null || searchResults.isBlank()) {
            throw new RuntimeException("搜索结果为空，请尝试其他关键词");
        }
        log.info("搜索完成，结果长度: {} 字符", searchResults.length());

        // Step 2: AI 生成标题
        log.info("Step 2: AI 生成标题...");
        String title = summarizeService.generateTitle(keyword, searchResults);
        log.info("标题生成完成: {}", title);

        // Step 3: AI 生成正文
        log.info("Step 3: AI 生成正文...");
        String contentBody = summarizeService.generateContent(keyword, searchResults);
        log.info("正文生成完成，长度: {} 字符", contentBody.length());

        // Step 4: 生成摘要（取前100字）
        String summary = contentBody.length() > 100 
                ? contentBody.substring(0, 100) + "..." 
                : contentBody;

        // Step 5: 存储内容
        log.info("Step 4: 存储内容到数据库...");
        ContentUpsertRequest request = new ContentUpsertRequest();
        request.setTitle(title);
        request.setSummary(summary);
        request.setContentBody(contentBody);
        request.setType("ARTICLE");
        request.setCategoryEntityId(categoryId);
        request.setPublishStatus("PUBLISHED");
        request.setPublishTime(LocalDateTime.now());
        request.setCoverUrl(""); // 可以后续添加 AI 生成封面图
        request.setTags(tags != null ? tags : List.of(keyword));

        ContentItemEntity result = contentFacadeService.saveContent(null, request);
        log.info("内容生成并存储成功，内容ID: {}", result.getId());

        return result;
    }

    /**
     * 仅搜索（不生成内容）。
     *
     * @param keyword 搜索关键词
     * @return 搜索结果
     */
    public String searchOnly(String keyword) {
        log.info("执行纯搜索，关键词: {}", keyword);
        return searchService.search(keyword);
    }

    /**
     * 根据搜索结果生成内容（不重新搜索）。
     *
     * @param keyword 内容关键词
     * @param searchResults 已有搜索结果
     * @param categoryId 分类ID
     * @param tags 标签列表
     * @return 生成的内容实体
     */
    public ContentItemEntity generateFromSearch(String keyword, String searchResults, 
                                                  Long categoryId, List<String> tags) {
        log.info("基于已有搜索结果生成内容，关键词: {}", keyword);

        String title = summarizeService.generateTitle(keyword, searchResults);
        String contentBody = summarizeService.generateContent(keyword, searchResults);
        String summary = contentBody.length() > 100 
                ? contentBody.substring(0, 100) + "..." 
                : contentBody;

        ContentUpsertRequest request = new ContentUpsertRequest();
        request.setTitle(title);
        request.setSummary(summary);
        request.setContentBody(contentBody);
        request.setType("ARTICLE");
        request.setCategoryEntityId(categoryId);
        request.setPublishStatus("PUBLISHED");
        request.setPublishTime(LocalDateTime.now());
        request.setTags(tags != null ? tags : List.of(keyword));

        return contentFacadeService.saveContent(null, request);
    }
}
