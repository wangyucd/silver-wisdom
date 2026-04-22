<template>
  <section class="space-y-5">
    <el-page-header content="用户详情" @back="router.push('/users')" />

    <el-skeleton v-if="loading" :rows="8" animated />

    <template v-else-if="detail">
      <el-card shadow="never" class="rounded-[24px] !border-white/70">
        <template #header>
          <div class="text-lg font-700 text-brand-900">基础信息</div>
        </template>
        <div class="grid grid-cols-3 gap-4">
          <div class="rounded-2xl bg-brand-50/65 p-4">
            <div class="text-sm text-brand-700/70">用户ID</div>
            <div class="mt-2 text-2xl font-700">{{ detail.userId }}</div>
          </div>
          <div class="rounded-2xl bg-brand-50/65 p-4">
            <div class="text-sm text-brand-700/70">昵称</div>
            <div class="mt-2 text-2xl font-700">{{ detail.nickname || '--' }}</div>
          </div>
          <div class="rounded-2xl bg-brand-50/65 p-4">
            <div class="text-sm text-brand-700/70">状态</div>
            <div class="mt-2 text-2xl font-700">{{ detail.status || '--' }}</div>
          </div>
          <div class="rounded-2xl bg-brand-50/65 p-4">
            <div class="text-sm text-brand-700/70">注册时间</div>
            <div class="mt-2 text-base font-600">{{ formatDateTime(detail.createdAt) }}</div>
          </div>
          <div class="rounded-2xl bg-brand-50/65 p-4">
            <div class="text-sm text-brand-700/70">最近登录</div>
            <div class="mt-2 text-base font-600">{{ formatDateTime(detail.lastLoginTime) }}</div>
          </div>
        </div>
      </el-card>

      <el-card shadow="never" class="rounded-[24px] !border-white/70">
        <template #header>
          <div class="text-lg font-700 text-brand-900">兴趣标签</div>
        </template>
        <div class="flex flex-wrap gap-3">
          <el-tag v-for="item in detail.tagList" :key="`${item.tag}-${item.weight}`" effect="plain" round>
            {{ item.tag }} · {{ item.weight }}
          </el-tag>
          <el-empty v-if="!detail.tagList?.length" description="用户暂未完成兴趣画像问卷" />
        </div>
      </el-card>

      <el-card shadow="never" class="rounded-[24px] !border-white/70">
        <template #header>
          <div class="text-lg font-700 text-brand-900">学习概览</div>
        </template>
        <div class="grid grid-cols-3 gap-4">
          <div class="rounded-2xl bg-sand-50 p-4">
            <div class="text-sm text-brand-700/70">已生成内容数</div>
            <div class="mt-2 text-2xl font-700">{{ detail.learningSummary?.generatedCount ?? 0 }}</div>
          </div>
          <div class="rounded-2xl bg-sand-50 p-4">
            <div class="text-sm text-brand-700/70">内容浏览次数</div>
            <div class="mt-2 text-2xl font-700">{{ detail.learningSummary?.contentViewCount ?? 0 }}</div>
          </div>
          <div class="rounded-2xl bg-sand-50 p-4">
            <div class="text-sm text-brand-700/70">最近学习时间</div>
            <div class="mt-2 text-base font-600">{{ formatDateTime(detail.learningSummary?.lastLearnTime) }}</div>
          </div>
        </div>
      </el-card>
    </template>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { fetchUserDetail } from '../api/users';
import { getAdminToken } from '../stores/auth';
import type { UserDetailResponse } from '../types/domain';
import { formatDateTime } from '../utils/format';
import { showRequestError } from '../utils/message';

const route = useRoute();
const router = useRouter();
const loading = ref(false);
const detail = ref<UserDetailResponse | null>(null);

onMounted(async () => {
  const token = getAdminToken();
  if (!token) {
    return;
  }
  const userId = Number(route.params.userId);
  loading.value = true;
  try {
    detail.value = await fetchUserDetail(token, userId);
  } catch (error) {
    showRequestError(error, '用户详情加载失败');
  } finally {
    loading.value = false;
  }
});
</script>
