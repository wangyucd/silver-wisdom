<template>
  <div class="min-h-screen flex items-center justify-center bg-[linear-gradient(180deg,#eef4ff_0%,#f8fbff_35%,#f4f0ea_100%)] px-6">
    <div class="w-full max-w-[460px] rounded-[28px] border border-white/65 bg-white/88 p-9 shadow-[0_24px_60px_rgba(32,84,147,0.12)] backdrop-blur">
      <div class="space-y-4">
        <span class="inline-flex rounded-full bg-brand-50 px-3 py-1 text-xs tracking-[0.2em] text-brand-500 uppercase">
          Admin Console
        </span>
        <div>
          <h1 class="text-3xl font-700 text-brand-900">银发智慧内容后台</h1>
          <p class="mt-2 text-sm leading-6 text-brand-700/70">
            使用 Vue 3、Element Plus 与 UnoCSS 重建的管理后台，覆盖管理员登录、用户管理与内容运营。
          </p>
        </div>
      </div>

      <el-form class="mt-8" :model="form" label-position="top" @submit.prevent="handleSubmit">
        <el-form-item label="账号">
          <el-input v-model="form.username" placeholder="请输入后台账号" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" show-password type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-alert v-if="errorText" :closable="false" :title="errorText" class="mb-4" type="error" />
        <el-button class="w-full !h-11" :loading="submitting" type="primary" @click="handleSubmit">
          登录后台
        </el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
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
