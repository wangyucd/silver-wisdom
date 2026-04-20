package com.silver.content.service;

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
import java.util.List;

public interface ContentFacadeService {

    List<Hotspot> activeHotspots();

    List<Hotspot> adminHotspots();

    Hotspot saveHotspot(Long hotspotId, HotspotUpsertRequest request);

    List<Category> categories();

    Category saveCategory(Long categoryId, CategoryUpsertRequest request);

    ContentPageResponse contentPage(Long categoryId, String sortBy, int pageNum, int pageSize);

    List<ContentItem> adminContents();

    ContentItem contentDetail(Long contentId);

    ContentItem saveContent(Long contentId, ContentUpsertRequest request);

    AiChatResponse chat(AiChatRequest request);

    AiGenerateTaskResponse generate(AiGenerateRequest request);

    AiGenerateTaskResponse taskDetail(String taskId);
}
