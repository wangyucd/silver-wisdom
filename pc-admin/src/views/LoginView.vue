<template>
  <div class="login-page">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="pattern"></div>
    </div>

    <div class="login-container">
      <!-- 左侧品牌区 -->
      <div class="brand-section">
        <div class="brand-content">
          <div class="logo-wrapper">
            <svg class="logo-icon" viewBox="0 0 48 48" fill="none">
              <circle cx="24" cy="24" r="22" stroke="currentColor" stroke-width="2"/>
              <path d="M24 12C18 12 14 18 14 24C14 30 18 36 24 36C30 36 34 30 34 24C34 18 30 12 24 12Z" stroke="currentColor" stroke-width="2"/>
              <circle cx="24" cy="24" r="4" fill="currentColor"/>
              <path d="M24 8V12M24 36V40M8 24H12M36 24H40" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
            </svg>
          </div>
          <h1 class="brand-title">银发智慧</h1>
          <p class="brand-subtitle">内容运营管理平台</p>
          <div class="brand-features">
            <div class="feature-item">
              <span class="feature-icon">📊</span>
              <span>数据可视化</span>
            </div>
            <div class="feature-item">
              <span class="feature-icon">👥</span>
              <span>用户管理</span>
            </div>
            <div class="feature-item">
              <span class="feature-icon">📝</span>
              <span>内容运营</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧登录区 -->
      <div class="form-section">
        <div class="login-card">
          <div class="login-header">
            <h2>欢迎回来</h2>
            <p>请登录您的管理员账号</p>
          </div>

          <el-form class="login-form" :model="form" @submit.prevent="handleSubmit">
            <div class="form-group">
              <label class="form-label">
                <span class="label-icon">👤</span>
                账号
              </label>
              <el-input 
                v-model="form.username" 
                placeholder="请输入管理员账号"
                size="large"
                :prefix-icon="UserIcon"
              />
            </div>

            <div class="form-group">
              <label class="form-label">
                <span class="label-icon">🔒</span>
                密码
              </label>
              <el-input 
                v-model="form.password" 
                show-password
                type="password"
                placeholder="请输入密码"
                size="large"
                :prefix-icon="LockIcon"
              />
            </div>

            <Transition name="fade">
              <div v-if="errorText" class="error-message">
                <span class="error-icon">⚠️</span>
                {{ errorText }}
              </div>
            </Transition>

            <el-button 
              class="login-btn"
              :loading="submitting"
              size="large"
              @click="handleSubmit"
            >
              <span v-if="!submitting">登 录</span>
              <span v-else>登录中...</span>
            </el-button>
          </el-form>

          <div class="login-footer">
            <p>默认测试账号：<code>admin</code> / <code>admin123</code></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, h } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ApiError } from '../api/http';
import { loginAdmin } from '../stores/auth';

const router = useRouter();
const route = useRoute();

const form = reactive({
  username: 'admin',
  password: 'admin123'
});
const submitting = ref(false);
const errorText = ref('');

const UserIcon = () => h('svg', { viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': '2' }, [
  h('circle', { cx: '12', cy: '8', r: '4' }),
  h('path', { d: 'M4 20C4 17 8 15 12 15C16 15 20 17 20 20' })
]);

const LockIcon = () => h('svg', { viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor', 'stroke-width': '2' }, [
  h('rect', { x: '5', y: '11', width: '14', height: '10', rx: '2' }),
  h('path', { d: 'M8 11V7C8 4.8 9.8 3 12 3C14.2 3 16 4.8 16 7V11' })
]);

async function handleSubmit() {
  submitting.value = true;
  errorText.value = '';
  try {
    await loginAdmin(form);
    await router.replace(String(route.query.redirect || '/'));
  } catch (error) {
    errorText.value = error instanceof ApiError ? error.message : '登录失败，请稍后重试';
  } finally {
    submitting.value = false;
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #fef7f0 0%, #fdf4eb 50%, #f8f0e8 100%);
  position: relative;
  overflow: hidden;
}

/* 背景装饰 */
.bg-decoration {
  position: absolute;
  inset: 0;
  pointer-events: none;
  overflow: hidden;
}

.circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.4;
}

.circle-1 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #e8d4c4 0%, #d4b8a0 100%);
  top: -100px;
  right: -100px;
  animation: float 20s ease-in-out infinite;
}

.circle-2 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #c9a87c 0%, #b8956a 100%);
  bottom: -50px;
  left: -50px;
  animation: float 15s ease-in-out infinite reverse;
}

.circle-3 {
  width: 200px;
  height: 200px;
  background: linear-gradient(135deg, #f0e0d0 0%, #e0d0c0 100%);
  top: 50%;
  left: 30%;
  animation: float 18s ease-in-out infinite;
}

.pattern {
  position: absolute;
  inset: 0;
  background-image: 
    radial-gradient(circle at 25% 25%, rgba(200, 169, 126, 0.1) 0%, transparent 50%),
    radial-gradient(circle at 75% 75%, rgba(180, 149, 107, 0.1) 0%, transparent 50%);
}

@keyframes float {
  0%, 100% { transform: translate(0, 0) scale(1); }
  33% { transform: translate(30px, -30px) scale(1.05); }
  66% { transform: translate(-20px, 20px) scale(0.95); }
}

/* 容器布局 */
.login-container {
  min-height: 100vh;
  display: flex;
  position: relative;
  z-index: 1;
}

/* 左侧品牌区 */
.brand-section {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px;
}

.brand-content {
  max-width: 420px;
}

.logo-wrapper {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #c9a87c 0%, #8b7355 100%);
  border-radius: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 20px 40px rgba(139, 115, 85, 0.3);
  margin-bottom: 32px;
}

.logo-icon {
  width: 48px;
  height: 48px;
  color: #fff;
}

.brand-title {
  font-size: 48px;
  font-weight: 700;
  color: #4a3728;
  margin: 0 0 12px 0;
  letter-spacing: 0.05em;
}

.brand-subtitle {
  font-size: 20px;
  color: #8b7355;
  margin: 0 0 48px 0;
}

.brand-features {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 20px;
  background: rgba(255, 255, 255, 0.6);
  border-radius: 16px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(200, 169, 126, 0.2);
  font-size: 16px;
  color: #5a4738;
  transition: all 0.3s ease;
}

.feature-item:hover {
  background: rgba(255, 255, 255, 0.8);
  transform: translateX(8px);
}

.feature-icon {
  font-size: 24px;
}

/* 右侧登录区 */
.form-section {
  width: 520px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px;
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(20px);
}

.login-card {
  width: 100%;
  max-width: 400px;
}

.login-header {
  margin-bottom: 40px;
  text-align: center;
}

.login-header h2 {
  font-size: 32px;
  font-weight: 700;
  color: #4a3728;
  margin: 0 0 12px 0;
}

.login-header p {
  font-size: 16px;
  color: #8b7355;
  margin: 0;
}

/* 表单样式 */
.login-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 600;
  color: #5a4738;
}

.label-icon {
  font-size: 16px;
}

.error-message {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  background: #fef2f2;
  border: 1px solid #fecaca;
  border-radius: 12px;
  color: #dc2626;
  font-size: 14px;
}

.error-icon {
  font-size: 16px;
}

.login-btn {
  width: 100%;
  height: 52px;
  background: linear-gradient(135deg, #c9a87c 0%, #8b7355 100%);
  border: none;
  border-radius: 14px;
  font-size: 18px;
  font-weight: 600;
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 8px 24px rgba(139, 115, 85, 0.3);
  margin-top: 8px;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(139, 115, 85, 0.4);
}

.login-btn:active {
  transform: translateY(0);
}

.login-footer {
  margin-top: 32px;
  text-align: center;
  font-size: 13px;
  color: #8b7355;
}

.login-footer code {
  background: rgba(200, 169, 126, 0.2);
  padding: 2px 8px;
  border-radius: 6px;
  font-family: monospace;
}

/* 过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* 响应式 */
@media (max-width: 1024px) {
  .brand-section {
    display: none;
  }
  
  .form-section {
    width: 100%;
  }
}
</style>
