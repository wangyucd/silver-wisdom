<template>
  <section class="space-y-5">
    <div class="rounded-[28px] bg-[linear-gradient(135deg,rgba(32,84,147,.94),rgba(16,37,66,.92))] px-7 py-8 text-white shadow-[0_24px_60px_rgba(32,84,147,0.18)]">
      <span class="inline-flex rounded-full bg-white/14 px-3 py-1 text-xs tracking-[0.14em] uppercase">运营总览</span>
      <h2 class="mt-4 text-3xl font-700 leading-tight">围绕用户、内容和学习结果的统一管理视图</h2>
      <p class="mt-3 max-w-[720px] text-sm leading-6 text-white/78">
        首页聚合后台当前最关键的三类数据，帮助你快速进入用户处置、内容运营和学习生成回查。
      </p>
    </div>

    <div class="grid grid-cols-3 gap-4">
      <div
        v-for="item in stats"
        :key="item.label"
        class="rounded-[24px] border border-white/65 bg-white/86 p-6 shadow-[0_24px_60px_rgba(32,84,147,0.12)]"
      >
        <p class="text-sm text-brand-700/72">{{ item.label }}</p>
        <p class="mt-2 text-4xl font-700 text-brand-900">{{ item.value }}</p>
      </div>
    </div>

    <el-card shadow="never" class="rounded-[24px] !border-white/70">
      <template #header>
        <div class="flex items-center justify-between">
          <div>
            <div class="text-lg font-700 text-brand-900">最近生成学习内容</div>
            <div class="text-sm text-brand-700/70">来自 `/course/learn/generated`</div>
          </div>
        </div>
      </template>

      <div v-if="recentLearn.length" class="space-y-3">
        <div
          v-for="item in recentLearn"
          :key="item.taskId"
          class="flex items-center justify-between rounded-2xl bg-brand-50/65 px-4 py-4"
        >
          <div>
            <div class="font-600 text-brand-900">{{ item.title }}</div>
            <div class="mt-1 text-sm text-brand-700/70">任务号：{{ item.taskId }}</div>
          </div>
          <span class="text-sm text-brand-700/70">{{ formatDateTime(item.createdAt) }}</span>
        </div>
      </div>
      <el-empty v-else description="暂无学习生成记录，可在小程序 AI 流程跑通后回到这里验证。" />
    </el-card>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { fetchGeneratedLearn } from '../api/content';
import { fetchUsers } from '../api/users';
import { getAdminToken } from '../stores/auth';
import { formatDateTime } from '../utils/format';
import { showRequestError } from '../utils/message';

const userTotal = ref(0);
const currentUserSample = ref(0);
const learnTotal = ref(0);
const recentLearn = ref<Array<{ taskId: string; title: string; createdAt?: string }>>([]);

const stats = computed(() => [
  { label: '用户总量', value: userTotal.value },
  { label: '当前页活跃样本', value: currentUserSample.value },
  { label: '学习生成记录', value: learnTotal.value }
]);

onMounted(async () => {
  const token = getAdminToken();
  if (!token) {
    return;
  }
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
    showRequestError(error, '首页数据加载失败');
  }
});
</script>
