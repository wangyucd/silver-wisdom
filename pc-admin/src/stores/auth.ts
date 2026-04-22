import { fetchProfile, login as loginApi, logout as logoutApi } from '../api/auth';
import type { AdminLoginRequest, AdminProfileResponse } from '../types/domain';

const TOKEN_KEY = 'silver-wisdom-admin-token';

export function getAdminToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function setAdminToken(token: string) {
  localStorage.setItem(TOKEN_KEY, token);
}

export function clearAdminToken() {
  localStorage.removeItem(TOKEN_KEY);
}

export async function loginAdmin(payload: AdminLoginRequest) {
  const result = await loginApi(payload);
  setAdminToken(result.token);
  return result;
}

export async function loadAdminProfile(): Promise<AdminProfileResponse | null> {
  const token = getAdminToken();
  if (!token) {
    return null;
  }
  try {
    return await fetchProfile(token);
  } catch {
    clearAdminToken();
    return null;
  }
}

export async function logoutAdmin() {
  const token = getAdminToken();
  if (token) {
    try {
      await logoutApi(token);
    } catch {
      // 本地登录态清理优先
    }
  }
  clearAdminToken();
}
