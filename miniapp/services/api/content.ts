import { request } from '../../utils/request';

export function fetchHotspots() {
  return request<HotspotEntity[]>('/content/hotspots/active', {
    needAuth: false
  });
}

export function fetchCategories() {
  return request<CategoryEntity[]>('/content/categories', {
    needAuth: false
  });
}

export function fetchContentPage(query: {
  categoryId?: number;
  sortBy?: string;
  pageNum?: number;
  pageSize?: number;
}) {
  return request<ContentPageResponse>('/content/items', {
    needAuth: false,
    data: query
  });
}

export function fetchContentDetail(contentId: number) {
  return request<ContentItemEntity>(`/content/items/${contentId}`, {
    needAuth: false
  });
}

export function chatWithAi(question: string, contextItemIds: number[]) {
  return request<AiChatResponse>('/content/ai/chat', {
    method: 'POST',
    data: {
      question,
      contextItemIds
    }
  });
}

export function generateByAi(prompt: string, scene: string) {
  return request<AiGenerateTaskResponse>('/content/ai/generate', {
    method: 'POST',
    data: {
      prompt,
      scene
    }
  });
}

export function fetchGenerateTask(taskId: string) {
  return request<AiGenerateTaskResponse>(`/content/ai/generate/${taskId}`);
}
