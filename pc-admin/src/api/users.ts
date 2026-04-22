import { request } from './http';
import type { UpdateUserStatusRequest, UserDetailResponse, UserPageResponse } from '../types/domain';

export function fetchUsers(
  token: string,
  query: {
    pageNum: number;
    pageSize: number;
    keyword?: string;
    status?: string;
  }
) {
  return request<UserPageResponse>('/user/admin/users', {
    token,
    query
  });
}

export function fetchUserDetail(token: string, userId: number) {
  return request<UserDetailResponse>(`/user/admin/users/${userId}`, { token });
}

export function updateUserStatus(token: string, userId: number, payload: UpdateUserStatusRequest) {
  return request<boolean>(`/user/admin/users/${userId}/status`, {
    method: 'POST',
    token,
    body: payload
  });
}
