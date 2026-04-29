<template>
  <div class="users-page">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="header-content">
        <h2 class="page-title">用户管理</h2>
        <p class="page-desc">管理平台用户账户，查看用户状态与信息</p>
      </div>
      <div class="header-actions">
        <el-button class="refresh-btn" @click="loadUsers">
          <span class="btn-icon">🔄</span>
          刷新列表
        </el-button>
      </div>
    </div>

    <!-- 筛选栏 -->
    <div class="filter-section">
      <div class="filter-group">
        <el-input 
          v-model="filters.keyword" 
          clearable 
          placeholder="搜索用户昵称或标签"
          class="search-input"
        >
          <template #prefix>
            <span class="search-icon">🔍</span>
          </template>
        </el-input>

        <el-select 
          v-model="filters.status" 
          clearable 
          placeholder="全部状态"
          class="status-select"
        >
          <template #prefix>
            <span class="search-icon">📋</span>
          </template>
          <el-option label="启用" value="ENABLED" />
          <el-option label="禁用" value="DISABLED" />
        </el-select>

        <el-button type="primary" class="search-btn" @click="handleSearch">
          查 询
        </el-button>
      </div>

      <div class="filter-summary">
        共找到 <span class="highlight">{{ total }}</span> 位用户
      </div>
    </div>

    <!-- 用户列表 -->
    <div class="users-section">
      <div v-if="loading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>加载中...</p>
      </div>

      <div v-else-if="users.length === 0" class="empty-state">
        <div class="empty-icon">👤</div>
        <p class="empty-title">暂无用户数据</p>
        <p class="empty-desc">当前筛选条件下没有找到用户</p>
      </div>

      <div v-else class="users-list">
        <div 
          v-for="user in users" 
          :key="user.userId"
          class="user-card"
        >
          <div class="user-avatar">
            {{ user.nickname?.charAt(0) || '?' }}
          </div>
          
          <div class="user-info">
            <div class="user-header">
              <span class="user-name">{{ user.nickname || '未知用户' }}</span>
              <span class="user-id">ID: {{ user.userId }}</span>
            </div>
            <div class="user-tags" v-if="user.tagSummary">
              <span class="tag">{{ joinTags(user.tagSummary) }}</span>
            </div>
            <div class="user-meta">
              <span class="meta-item">
                <span class="meta-icon">🕐</span>
                最近登录：{{ formatDateTime(user.lastLoginTime) || '从未登录' }}
              </span>
              <span class="meta-item">
                <span class="meta-icon">📅</span>
                注册时间：{{ formatDateTime(user.createdAt) }}
              </span>
            </div>
          </div>

          <div class="user-status">
            <span 
              class="status-badge"
              :class="user.status === 'ENABLED' ? 'status-active' : 'status-disabled'"
            >
              {{ user.status === 'ENABLED' ? '启用' : '禁用' }}
            </span>
          </div>

          <div class="user-actions">
            <el-button class="action-btn action-detail" @click="goDetail(user.userId)">
              <span>查看详情</span>
            </el-button>
            <el-dropdown trigger="click" @command="(cmd: string) => changeStatus(user.userId, cmd)">
              <el-button class="action-btn action-more">
                <span>更多操作</span>
                <span class="arrow">▼</span>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="ENABLED" :disabled="user.status === 'ENABLED'">
                    ✅ 设为启用
                  </el-dropdown-item>
                  <el-dropdown-item command="DISABLED" :disabled="user.status === 'DISABLED'">
                    ⛔ 设为禁用
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>

      <!-- 分页 -->
      <div v-if="users.length > 0" class="pagination-wrapper">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          background
          layout="total, prev, pager, next"
          :total="total"
          @current-change="loadUsers"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElNotification } from 'element-plus';
import { fetchUsers, updateUserStatus } from '../api/users';
import { getAdminToken } from '../stores/auth';
import type { UserListItem } from '../types/domain';
import { formatDateTime, joinTags } from '../utils/format';
import { showRequestError } from '../utils/message';

const router = useRouter();
const loading = ref(false);
const users = ref<UserListItem[]>([]);
const total = ref(0);
const filters = reactive({
  keyword: '',
  status: ''
});
const pagination = reactive({
  pageNum: 1,
  pageSize: 10
});

onMounted(() => {
  void loadUsers();
});

async function loadUsers() {
  const token = getAdminToken();
  if (!token) return;
  
  loading.value = true;
  try {
    const result = await fetchUsers(token, {
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
      keyword: filters.keyword.trim() || undefined,
      status: filters.status || undefined
    });
    users.value = result.list;
    total.value = result.total;
  } catch (error) {
    showRequestError(error, '用户列表加载失败');
  } finally {
    loading.value = false;
  }
}

function handleSearch() {
  pagination.pageNum = 1;
  void loadUsers();
}

function goDetail(userId: number) {
  void router.push(`/users/${userId}`);
}

async function changeStatus(userId: number, status: string) {
  const token = getAdminToken();
  if (!token) return;
  
  try {
    await updateUserStatus(token, userId, {
      status,
      reason: '后台手动调整'
    });
    ElNotification.success({
      title: '操作成功',
      message: `用户 ${userId} 已${status === 'ENABLED' ? '启用' : '禁用'}`
    });
    await loadUsers();
  } catch (error) {
    showRequestError(error, '状态更新失败');
  }
}
</script>

<style scoped>
.users-page {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

/* 页面标题 */
.page-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
}

.header-content {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.page-title {
  font-size: 24px;
  font-weight: 700;
  color: #4a3728;
  margin: 0;
}

.page-desc {
  font-size: 14px;
  color: #8b7355;
  margin: 0;
}

.refresh-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #fff;
  border: 1px solid rgba(74, 55, 40, 0.15);
  border-radius: 12px;
  color: #4a3728;
  font-weight: 600;
  transition: all 0.3s ease;
}

.refresh-btn:hover {
  background: #fef7f0;
  border-color: rgba(201, 168, 124, 0.3);
}

.btn-icon {
  font-size: 16px;
}

/* 筛选栏 */
.filter-section {
  background: #fff;
  border-radius: 16px;
  padding: 20px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 2px 12px rgba(74, 55, 40, 0.04);
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 12px;
}

.search-input {
  width: 240px;
}

.search-icon {
  font-size: 16px;
}

.status-select {
  width: 160px;
}

.search-btn {
  padding: 10px 24px;
  background: linear-gradient(135deg, #c9a87c 0%, #8b7355 100%);
  border: none;
  border-radius: 10px;
  font-weight: 600;
  color: #fff;
  transition: all 0.3s ease;
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(139, 115, 85, 0.3);
}

.filter-summary {
  font-size: 14px;
  color: #8b7355;
}

.filter-summary .highlight {
  color: #c9a87c;
  font-weight: 700;
}

/* 用户列表 */
.users-section {
  background: #fff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(74, 55, 40, 0.04);
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #8b7355;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid rgba(201, 168, 124, 0.2);
  border-top-color: #c9a87c;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
}

.empty-icon {
  font-size: 64px;
  margin-bottom: 16px;
}

.empty-title {
  font-size: 18px;
  font-weight: 700;
  color: #4a3728;
  margin: 0 0 8px 0;
}

.empty-desc {
  font-size: 14px;
  color: #8b7355;
  margin: 0;
}

/* 用户卡片 */
.users-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.user-card {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 24px;
  background: #faf8f5;
  border-radius: 14px;
  transition: all 0.3s ease;
}

.user-card:hover {
  background: #fef7f0;
  transform: translateX(8px);
}

.user-avatar {
  width: 52px;
  height: 52px;
  background: linear-gradient(135deg, #c9a87c 0%, #e8d4c4 100%);
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  font-weight: 700;
  color: #4a3728;
  flex-shrink: 0;
}

.user-info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.user-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-name {
  font-size: 16px;
  font-weight: 700;
  color: #4a3728;
}

.user-id {
  font-size: 12px;
  color: #8b7355;
  background: rgba(139, 115, 85, 0.1);
  padding: 2px 8px;
  border-radius: 6px;
}

.user-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.tag {
  font-size: 12px;
  color: #8b7355;
  background: rgba(201, 168, 124, 0.15);
  padding: 4px 10px;
  border-radius: 6px;
}

.user-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #8b7355;
}

.meta-icon {
  font-size: 14px;
}

/* 用户状态 */
.user-status {
  flex-shrink: 0;
}

.status-badge {
  padding: 6px 14px;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 600;
}

.status-active {
  background: #dcfce7;
  color: #166534;
}

.status-disabled {
  background: #fee2e2;
  color: #991b1b;
}

/* 用户操作 */
.user-actions {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-shrink: 0;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 14px;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.action-detail {
  background: rgba(201, 168, 124, 0.15);
  color: #8b7355;
  border: none;
}

.action-detail:hover {
  background: rgba(201, 168, 124, 0.25);
  color: #4a3728;
}

.action-more {
  background: #fff;
  color: #4a3728;
  border: 1px solid rgba(74, 55, 40, 0.15);
}

.action-more:hover {
  border-color: rgba(201, 168, 124, 0.3);
}

.arrow {
  font-size: 10px;
}

/* 分页 */
.pagination-wrapper {
  margin-top: 24px;
  display: flex;
  justify-content: center;
}

:deep(.el-pagination) {
  --el-pagination-bg-color: transparent;
  --el-pagination-button-bg-color: #faf8f5;
  --el-pagination-hover-color: #c9a87c;
}

:deep(.el-pagination.is-background .el-pager li.is-active) {
  background: linear-gradient(135deg, #c9a87c 0%, #8b7355 100%);
  color: #fff;
}
</style>
