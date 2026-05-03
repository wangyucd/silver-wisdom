package com.silver.content.service.ai.search;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * DuckDuckGo 搜索引擎服务。
 * 用于搜索互联网内容，获取与关键词相关的摘要信息。
 *
 * @author wangyu03
 * @since 2026/04/29
 */
@Slf4j
@Service
public class DuckDuckGoSearchService {

    private static final String DUCK_DUCK_GO_URL = "https://html.duckduckgo.com/html/?q=";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final int TIMEOUT_MS = 10000;
    private static final int MAX_RESULTS = 5;

    /**
     * 搜索关键词并返回搜索结果摘要。
     *
     * @param query 搜索关键词
     * @return 搜索结果摘要，多条结果用换行分隔
     */
    public String search(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = DUCK_DUCK_GO_URL + encodedQuery;

            log.info("开始 DuckDuckGo 搜索: {}", query);

            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(TIMEOUT_MS)
                    .get();

            Elements results = doc.select(".result__body");
            StringBuilder sb = new StringBuilder();

            int resultCount = Math.min(results.size(), MAX_RESULTS);
            for (int i = 0; i < resultCount; i++) {
                var result = results.get(i);
                String title = result.select(".result__title").text();
                String snippet = result.select(".result__snippet").text();

                sb.append("标题：").append(title).append("\n");
                sb.append("摘要：").append(snippet).append("\n\n");
            }

            String searchResult = sb.toString();
            log.info("DuckDuckGo 搜索完成，返回 {} 条结果", resultCount);

            return searchResult;
        } catch (Exception e) {
            log.error("DuckDuckGo 搜索失败: {}", query, e);
            return "搜索服务暂时不可用，请稍后重试。";
        }
    }
}
