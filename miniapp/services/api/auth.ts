import { request } from '../../utils/request';

export function wxLogin(code: string) {
  return request<LoginResponse>('/user/auth/wx-login', {
    method: 'POST',
    needAuth: false,
    data: { code }
  });
}

export function fetchCurrentUser() {
  return request<CurrentUserResponse>('/user/auth/me');
}

export function logout() {
  return request<boolean>('/user/auth/logout', {
    method: 'POST'
  });
}
