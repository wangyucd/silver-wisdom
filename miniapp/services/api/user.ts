import { request } from '../../utils/request';

export function fetchCurrentTags() {
  return request<UserTagResponse>('/user/profile/tags');
}

export function saveOnboarding(tags: string[], source: string) {
  return request<UserTagResponse>('/user/profile/onboarding', {
    method: 'POST',
    data: {
      tags,
      source
    }
  });
}
