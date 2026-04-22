import type { ApiResponse } from '../types/api';

const API_BASE_URL =
  (import.meta.env.VITE_API_BASE_URL as string | undefined)?.trim() || 'http://localhost:8080';

export class ApiError extends Error {
  code: number;

  constructor(message: string, code = 500) {
    super(message);
    this.code = code;
  }
}

type RequestOptions = {
  method?: 'GET' | 'POST';
  body?: unknown;
  token?: string | null;
  query?: Record<string, string | number | undefined | null>;
};

function buildUrl(path: string, query?: RequestOptions['query']) {
  const url = new URL(path, API_BASE_URL);
  Object.entries(query ?? {}).forEach(([key, value]) => {
    if (value !== undefined && value !== null && value !== '') {
      url.searchParams.set(key, String(value));
    }
  });
  return url.toString();
}

export async function request<T>(path: string, options: RequestOptions = {}): Promise<T> {
  const response = await fetch(buildUrl(path, options.query), {
    method: options.method ?? 'GET',
    headers: {
      'Content-Type': 'application/json',
      ...(options.token ? { Authorization: `Bearer ${options.token}` } : {})
    },
    body: options.body === undefined ? undefined : JSON.stringify(options.body)
  });

  if (!response.ok) {
    throw new ApiError(`请求失败: ${response.status}`, response.status);
  }

  const result = (await response.json()) as ApiResponse<T>;
  if (result.code !== 200) {
    throw new ApiError(result.message || '服务异常', result.code);
  }

  return result.data;
}

export { API_BASE_URL };
