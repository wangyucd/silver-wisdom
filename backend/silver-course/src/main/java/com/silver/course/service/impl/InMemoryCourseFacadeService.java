package com.silver.course.service.impl;

import com.silver.course.model.GeneratedLearnItem;
import com.silver.course.model.GeneratedLearnPageResponse;
import com.silver.course.service.CourseFacadeService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InMemoryCourseFacadeService implements CourseFacadeService {

    private final List<GeneratedLearnItem> items = List.of(
            buildItem("task_seed_001", "7天宠物陪伴计划", "帮助银发用户逐步建立稳定的宠物护理节奏。", LocalDateTime.now().minusHours(2)),
            buildItem("task_seed_002", "经典小说共读清单", "围绕 3 本适合亲子交流的小说给出共读建议。", LocalDateTime.now().minusDays(1)),
            buildItem("task_seed_003", "二次元入门学习路径", "从轻量作品到概念梳理的 5 天学习规划。", LocalDateTime.now().minusDays(2))
    );

    @Override
    public GeneratedLearnPageResponse generatedPage(int pageNum, int pageSize) {
        int normalizedPageNum = Math.max(pageNum, 1);
        int normalizedPageSize = Math.max(pageSize, 1);
        int fromIndex = Math.min((normalizedPageNum - 1) * normalizedPageSize, items.size());
        int toIndex = Math.min(fromIndex + normalizedPageSize, items.size());
        GeneratedLearnPageResponse response = new GeneratedLearnPageResponse();
        response.setTotal(items.size());
        response.setList(items.subList(fromIndex, toIndex));
        return response;
    }

    private GeneratedLearnItem buildItem(String taskId, String title, String summary, LocalDateTime createdAt) {
        GeneratedLearnItem item = new GeneratedLearnItem();
        item.setTaskId(taskId);
        item.setTitle(title);
        item.setSummary(summary);
        item.setCreatedAt(createdAt);
        return item;
    }
}
