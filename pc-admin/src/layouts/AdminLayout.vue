<template>
  <div class="admin-layout">
    <!-- 侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <div class="logo">
          <svg class="logo-icon" viewBox="0 0 40 40" fill="none">
            <circle cx="20" cy="20" r="18" stroke="currentColor" stroke-width="2"/>
            <path d="M20 10C15 10 12 15 12 20C12 25 15 30 20 30C25 30 28 25 28 20C28 15 25 10 20 10Z" stroke="currentColor" stroke-width="2"/>
            <circle cx="20" cy="20" r="3" fill="currentColor"/>
          </svg>
        </div>
        <div class="brand">
          <h1>银发智慧</h1>
          <p>运营管理后台</p>
        </div>
      </div>

      <nav class="sidebar-nav">
        <router-link 
          v-for="item in navItems" 
          :key="item.path"
          :to="item.path"
          class="nav-item"
          :class="{ active: isActive(item.path) }"
        >
          <span class="nav-icon">{{ item.icon }}</span>
          <span class="nav-text">{{ item.label }}</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <div class="user-info">
          <div class="user-avatar">
            {{ profile?.name?.charAt(0) || 'A' }}
          </div>
          <div class="user-detail">
            <span class="user-name">{{ profile?.name || '管理员' }}</span>
            <span class="user-role">系统管理员</span>
          </div>
        </div>
        <button class="logout-btn" @click="handleLogout">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
            <polyline points="16 17 21 12 16 7"/>
            <line x1="21" y1="12" x2="9" y2="12"/>
          </svg>
        </button>
      </div>
    </aside>

    <!-- 主内容区 -->
    <main class="main-content">
      <header class="top-header">
        <div class="header-left">
          <h2 class="page-title">{{ currentPageTitle }}</h2>
          <span class="header-time">{{ currentTime }}</span>
        </div>
        <div class="header-right">
          <div class="header-badge">
            <span class="badge-dot"></span>
            系统运行正常
          </div>
        </div>
      </header>

      <div class="content-area">
        <router-view v-slot="{ Component }">
          <Transition name="page" mode="out-in">
            <component :is="Component" />
          </Transition>
        </router-view>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessageBox } from 'element-plus';
import { useAdminSession } from '../composables/useAdminSession';

const route = useRoute();
const router = useRouter();
const { profile, refreshProfile, signOut } = useAdminSession();

const currentTime = ref('');

const navItems = [
  { path: '/', label: '运营概览', icon: '📊' },
  { path: '/users', label: '用户管理', icon: '👥' },
  { path: '/content', label: '内容管理', icon: '📝' },
];

const currentPageTitle = computed(() => {
  const item = navItems.find(n => n.path === route.path);
  if (item) return item.label;
  if (route.path.startsWith('/users/')) return '用户详情';
  return '银发智慧';
});

function isActive(path: string) {
  if (path === '/') return route.path === '/';
  return route.path.startsWith(path);
}

function updateTime() {
  const now = new Date();
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long',
    hour: '2-digit',
    minute: '2-digit'
  });
}

onMounted(() => {
  void refreshProfile();
  updateTime();
  setInterval(updateTime, 60000);
});

async function handleLogout() {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      type: 'warning',
      confirmButtonText: '确定',
      cancelButtonText: '取消',
    });
    await signOut();
    await router.push('/login');
  } catch {
    // 用户取消
  }
}
</script>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background: #faf8f5;
}

/* 侧边栏 */
.sidebar {
  width: 280px;
  background: linear-gradient(180deg, #4a3728 0%, #3d2d20 100%);
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
  z-index: 100;
}

.sidebar-header {
  padding: 32px 24px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.logo {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #c9a87c 0%, #e8d4c4 100%);
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 16px;
}

.logo-icon {
  width: 32px;
  height: 32px;
  color: #4a3728;
}

.brand h1 {
  font-size: 22px;
  font-weight: 700;
  color: #fff;
  margin: 0 0 4px 0;
}

.brand p {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.6);
  margin: 0;
}

/* 导航 */
.sidebar-nav {
  flex: 1;
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 14px 18px;
  border-radius: 14px;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: all 0.3s ease;
  font-size: 15px;
  font-weight: 500;
}

.nav-item:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}

.nav-item.active {
  background: linear-gradient(135deg, rgba(201, 168, 124, 0.3) 0%, rgba(201, 168, 124, 0.1) 100%);
  color: #e8d4c4;
  border: 1px solid rgba(201, 168, 124, 0.3);
}

.nav-icon {
  font-size: 20px;
}

/* 侧边栏底部 */
.sidebar-footer {
  padding: 20px 16px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #c9a87c 0%, #e8d4c4 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 16px;
  color: #4a3728;
}

.user-detail {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #fff;
}

.user-role {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.5);
}

.logout-btn {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.logout-btn svg {
  width: 20px;
  height: 20px;
  color: rgba(255, 255, 255, 0.7);
}

.logout-btn:hover {
  background: rgba(220, 38, 38, 0.2);
}

.logout-btn:hover svg {
  color: #fca5a5;
}

/* 主内容区 */
.main-content {
  flex: 1;
  margin-left: 280px;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* 顶部导航 */
.top-header {
  height: 72px;
  background: #fff;
  border-bottom: 1px solid rgba(74, 55, 40, 0.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  position: sticky;
  top: 0;
  z-index: 50;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.page-title {
  font-size: 20px;
  font-weight: 700;
  color: #4a3728;
  margin: 0;
}

.header-time {
  font-size: 13px;
  color: #8b7355;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-badge {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: #f0fdf4;
  border-radius: 20px;
  font-size: 13px;
  color: #166534;
}

.badge-dot {
  width: 8px;
  height: 8px;
  background: #22c55e;
  border-radius: 50%;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

/* 内容区 */
.content-area {
  flex: 1;
  padding: 32px;
}

/* 页面过渡动画 */
.page-enter-active,
.page-leave-active {
  transition: all 0.3s ease;
}

.page-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.page-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
</style>
