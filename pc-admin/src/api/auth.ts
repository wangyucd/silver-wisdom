import { request } from './http';
import type { AdminLoginRequest, AdminLoginResponse, AdminProfileResponse } from '../types/domain';

export function login(payload: AdminLoginRequest) {
  return request<AdminLoginResponse>('/user/admin/auth/login', {
    method: 'POST',
    body: payload
  });
}

export function fetchProfile(token: string) {
  return request<AdminProfileResponse>('/user/admin/auth/me', { token });
}

export function logout(token: string) {
  return request<boolean>('/user/admin/auth/logout', {
    method: 'POST',
    token
  });
}
