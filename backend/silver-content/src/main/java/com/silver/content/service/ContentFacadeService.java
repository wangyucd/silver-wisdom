package com.silver.content.service;

import com.silver.content.model.CategoryEntity;
import com.silver.content.model.ContentItemEntity;
import com.silver.content.model.HotspotEntity;
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

    List<HotspotEntity> activeHotspots();

    List<HotspotEntity> adminHotspots();

    HotspotEntity saveHotspot(Long hotspotId, HotspotUpsertRequest request);

    List<CategoryEntity> categories();

    CategoryEntity saveCategory(Long categoryId, CategoryUpsertRequest request);

    ContentPageResponse contentPage(Long categoryId, String sortBy, int pageNum, int pageSize);

    List<ContentItemEntity> adminContents();

    ContentItemEntity contentDetail(Long contentId);

    ContentItemEntity saveContent(Long contentId, ContentUpsertRequest request);

    AiChatResponse chat(AiChatRequest request);

    AiGenerateTaskResponse generate(AiGenerateRequest request);

    AiGenerateTaskResponse taskDetail(String taskId);
}
