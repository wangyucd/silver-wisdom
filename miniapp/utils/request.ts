import { getToken } from './auth';

const BASE_URL = 'http://localhost:8080';

type RequestOptions = {
  method?: 'GET' | 'POST';
  data?: Record<string, unknown>;
  needAuth?: boolean;
};

export function request<T>(url: string, options: RequestOptions = {}): Promise<T> {
  return new Promise((resolve, reject) => {
    wx.request<ApiResponse<T>>({
      url: `${BASE_URL}${url}`,
      method: options.method || 'GET',
      data: options.data,
      header: {
        'content-type': 'application/json',
        ...(options.needAuth === false ? {} : getToken() ? { Authorization: `Bearer ${getToken()}` } : {})
      },
      success: (response) => {
        const body = response.data;
        if (body.code !== 200) {
          wx.showToast({ title: body.message || '服务异常', icon: 'none' });
          reject(new Error(body.message || '服务异常'));
          return;
        }
        resolve(body.data);
      },
      fail: (error) => {
        wx.showToast({ title: '网络异常，请稍后重试', icon: 'none' });
        reject(error);
      }
    });
  });
}
