<template>
  <div class="dashboard">
    <!-- 统计卡片 -->
    <div class="stats-grid">
      <div class="stat-card stat-card-primary">
        <div class="stat-icon">👥</div>
        <div class="stat-content">
          <span class="stat-label">用户总量</span>
          <span class="stat-value">{{ userTotal }}</span>
        </div>
        <div class="stat-decoration">01</div>
      </div>

      <div class="stat-card stat-card-success">
        <div class="stat-icon">✨</div>
        <div class="stat-content">
          <span class="stat-label">活跃样本</span>
          <span class="stat-value">{{ currentUserSample }}</span>
        </div>
        <div class="stat-decoration">02</div>
      </div>

      <div class="stat-card stat-card-warning">
        <div class="stat-icon">📚</div>
        <div class="stat-content">
          <span class="stat-label">学习记录</span>
          <span class="stat-value">{{ learnTotal }}</span>
        </div>
        <div class="stat-decoration">03</div>
      </div>
    </div>

    <!-- 快捷操作 -->
    <div class="quick-actions">
      <h3 class="section-title">快捷操作</h3>
      <div class="actions-grid">
        <router-link to="/users" class="action-card">
          <span class="action-icon">🔍</span>
          <span class="action-text">用户查询</span>
        </router-link>
        <router-link to="/content" class="action-card">
          <span class="action-icon">✏️</span>
          <span class="action-text">内容编辑</span>
        </router-link>
        <button class="action-card" @click="loadData">
          <span class="action-icon">🔄</span>
          <span class="action-text">刷新数据</span>
        </button>
      </div>
    </div>

    <!-- 学习记录列表 -->
    <div class="recent-section">
      <div class="section-header">
        <h3 class="section-title">最近生成的学习内容</h3>
        <router-link to="/content" class="section-link">
          查看全部
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M5 12h14M12 5l7 7-7 7"/>
          </svg>
        </router-link>
      </div>

      <div v-if="recentLearn.length" class="learn-list">
        <div 
          v-for="item in recentLearn" 
          :key="item.taskId"
          class="learn-item"
        >
          <div class="learn-icon">📄</div>
          <div class="learn-info">
            <span class="learn-title">{{ item.title || '未命名内容' }}</span>
            <span class="learn-meta">
              任务编号：{{ item.taskId }}
              <span class="dot">·</span>
              {{ formatDateTime(item.createdAt) }}
            </span>
          </div>
          <div class="learn-status">
            <span class="status-badge status-success">已完成</span>
          </div>
        </div>
      </div>

      <div v-else class="empty-state">
        <div class="empty-icon">📭</div>
        <p class="empty-title">暂无学习记录</p>
        <p class="empty-desc">小程序 AI 流程跑通后，这里将展示生成的学习内容</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { fetchGeneratedLearn } from '../api/content';
import { fetchUsers } from '../api/users';
import { getAdminToken } from '../stores/auth';
import { formatDateTime } from '../utils/format';
import { showRequestError } from '../utils/message';

const userTotal = ref(0);
const currentUserSample = ref(0);
const learnTotal = ref(0);
const recentLearn = ref<Array<{ taskId: string; title: string; createdAt?: string }>>([]);

async function loadData() {
  const token = getAdminToken();
  if (!token) return;
  
  try {
    const [users, learn] = await Promise.all([
      fetchUsers(token, { pageNum: 1, pageSize: 5 }),
      fetchGeneratedLearn(token, 1, 5)
    ]);
    userTotal.value = users.total;
    currentUserSample.value = users.list.length;
    learnTotal.value = learn.total;
    recentLearn.value = learn.list;
  } catch (error) {
    showRequestError(error, '数据加载失败');
  }
}

onMounted(() => {
  void loadData();
});
</script>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 28px;
}

/* 统计卡片 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.stat-card {
  background: #fff;
  border-radius: 20px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 20px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(74, 55, 40, 0.06);
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 30px rgba(74, 55, 40, 0.1);
}

.stat-card-primary {
  background: linear-gradient(135deg, #fef7f0 0%, #fdf4eb 100%);
  border: 1px solid rgba(201, 168, 124, 0.2);
}

.stat-card-success {
  background: linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%);
  border: 1px solid rgba(34, 197, 94, 0.2);
}

.stat-card-warning {
  background: linear-gradient(135deg, #fffbeb 0%, #fef3c7 100%);
  border: 1px solid rgba(251, 191, 36, 0.2);
}

.stat-icon {
  font-size: 40px;
  width: 64px;
  height: 64px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.stat-content {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.stat-label {
  font-size: 14px;
  color: #8b7355;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #4a3728;
}

.stat-decoration {
  position: absolute;
  right: -10px;
  bottom: -10px;
  font-size: 80px;
  font-weight: 800;
  color: rgba(74, 55, 40, 0.05);
  font-family: serif;
}

/* 快捷操作 */
.quick-actions {
  background: #fff;
  border-radius: 20px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(74, 55, 40, 0.06);
}

.section-title {
  font-size: 18px;
  font-weight: 700;
  color: #4a3728;
  margin: 0 0 20px 0;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.action-card {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 18px 20px;
  background: #faf8f5;
  border: 1px solid rgba(74, 55, 40, 0.08);
  border-radius: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
}

.action-card:hover {
  background: #fef7f0;
  border-color: rgba(201, 168, 124, 0.3);
  transform: translateX(8px);
}

.action-icon {
  font-size: 24px;
}

.action-text {
  font-size: 15px;
  font-weight: 600;
  color: #4a3728;
}

/* 学习记录 */
.recent-section {
  background: #fff;
  border-radius: 20px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(74, 55, 40, 0.06);
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.section-link {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: #c9a87c;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
}

.section-link:hover {
  color: #8b7355;
}

.section-link svg {
  width: 16px;
  height: 16px;
}

.learn-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.learn-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 18px 20px;
  background: #faf8f5;
  border-radius: 14px;
  transition: all 0.3s ease;
}

.learn-item:hover {
  background: #fef7f0;
  transform: translateX(8px);
}

.learn-icon {
  font-size: 28px;
  width: 48px;
  height: 48px;
  background: #fff;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.learn-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.learn-title {
  font-size: 15px;
  font-weight: 600;
  color: #4a3728;
}

.learn-meta {
  font-size: 13px;
  color: #8b7355;
}

.dot {
  margin: 0 8px;
}

.status-badge {
  padding: 6px 12px;
  border-radius: 8px;
  font-size: 12px;
  font-weight: 600;
}

.status-success {
  background: #dcfce7;
  color: #166534;
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: 48px 20px;
}

.empty-icon {
  font-size: 56px;
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
</style>
