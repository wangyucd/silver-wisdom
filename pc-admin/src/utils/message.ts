import { ElMessage } from 'element-plus';
import { ApiError } from '../api/http';

export function showRequestError(error: unknown, fallback = '请求失败，请稍后重试') {
  if (error instanceof ApiError) {
    ElMessage.error(error.message);
    return;
  }
  ElMessage.error(fallback);
}
