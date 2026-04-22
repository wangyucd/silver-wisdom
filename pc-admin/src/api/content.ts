import { request } from './http';
import type {
  CategoryEntity,
  CategoryUpsertRequest,
  ContentItemEntity,
  ContentListQuery,
  ContentUpsertRequest,
  GeneratedLearnPageResponse,
  HotspotEntity,
  HotspotUpsertRequest
} from '../types/domain';

export function fetchHotspots(token: string) {
  return request<HotspotEntity[]>('/content/admin/hotspots', { token });
}

export function saveHotspot(token: string, payload: HotspotUpsertRequest, hotspotId?: number) {
  return request<HotspotEntity>(hotspotId ? `/content/admin/hotspots/${hotspotId}` : '/content/admin/hotspots', {
    method: 'POST',
    token,
    body: payload
  });
}

export function fetchCategories(token: string) {
  return request<CategoryEntity[]>('/content/categories', { token });
}

export function saveCategory(token: string, payload: CategoryUpsertRequest, categoryId?: number) {
  return request<CategoryEntity>(
    categoryId ? `/content/admin/categories/${categoryId}` : '/content/admin/categories',
    {
      method: 'POST',
      token,
      body: payload
    }
  );
}

export function fetchAdminContents(token: string) {
  return request<ContentItemEntity[]>('/content/admin/contents', { token });
}

export function saveContent(token: string, payload: ContentUpsertRequest, contentId?: number) {
  return request<ContentItemEntity>(
    contentId ? `/content/admin/contents/${contentId}` : '/content/admin/contents',
    {
      method: 'POST',
      token,
      body: {
        ...payload,
        categoryId: payload.categoryId
      }
    }
  );
}

export function fetchGeneratedLearn(token: string, pageNum = 1, pageSize = 10) {
  return request<GeneratedLearnPageResponse>('/course/learn/generated', {
    token,
    query: { pageNum, pageSize }
  });
}

export function fetchPublicContent(token: string, query: ContentListQuery) {
  return request('/content/items', {
    token,
    query: query as Record<string, string | number | undefined | null>
  });
}
