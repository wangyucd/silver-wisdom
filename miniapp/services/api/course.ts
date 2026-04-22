import { request } from '../../utils/request';

export function fetchGeneratedLearn(pageNum = 1, pageSize = 10) {
  return request<GeneratedLearnPageResponse>('/course/learn/generated', {
    data: { pageNum, pageSize }
  });
}
