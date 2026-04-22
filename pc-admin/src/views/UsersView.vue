<template>
  <section class="space-y-5">
    <el-card shadow="never" class="rounded-[24px] !border-white/70">
      <template #header>
        <div class="flex items-center justify-between">
          <div>
            <div class="text-lg font-700 text-brand-900">用户管理</div>
            <div class="text-sm text-brand-700/70">筛选、分页、状态处置都映射 `/user/admin/users`</div>
          </div>
          <el-button @click="loadUsers">立即刷新</el-button>
        </div>
      </template>

      <div class="mb-5 grid grid-cols-[1.2fr,220px,120px] gap-4">
        <el-input v-model="filters.keyword" clearable placeholder="搜索昵称或标签" />
        <el-select v-model="filters.status" clearable placeholder="全部状态">
          <el-option label="ACTIVE" value="ACTIVE" />
          <el-option label="DISABLED" value="DISABLED" />
        </el-select>
        <el-button type="primary" @click="handleSearch">查询</el-button>
      </div>

      <el-table v-loading="loading" :data="users" border>
        <el-table-column label="用户ID" prop="userId" width="110" />
        <el-table-column label="昵称" prop="nickname" min-width="140" />
        <el-table-column label="标签摘要" min-width="220">
          <template #default="{ row }">
            {{ joinTags(row.tagSummary) }}
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="120">
          <template #default="{ row }">
            <el-tag effect="plain">{{ row.status || 'UNKNOWN' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="最近登录" min-width="170">
          <template #default="{ row }">
            {{ formatDateTime(row.lastLoginTime) }}
          </template>
        </el-table-column>
        <el-table-column label="注册时间" min-width="170">
          <template #default="{ row }">
            {{ formatDateTime(row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="250" fixed="right">
          <template #default="{ row }">
            <div class="flex items-center gap-3">
              <el-button link type="primary" @click="goDetail(row.userId)">查看详情</el-button>
              <el-button link type="success" @click="changeStatus(row.userId, 'ACTIVE')">设为启用</el-button>
              <el-button link type="danger" @click="changeStatus(row.userId, 'DISABLED')">设为停用</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <div class="mt-5 flex justify-end">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          background
          layout="total, prev, pager, next"
          :total="total"
          @current-change="loadUsers"
        />
      </div>
    </el-card>
  </section>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessageBox, ElNotification } from 'element-plus';
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
  if (!token) {
    return;
  }
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
  if (!token) {
    return;
  }
  try {
    const { value } = await ElMessageBox.prompt('请输入状态变更原因', '更新用户状态', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      inputValue: '后台手动调整'
    });
    await updateUserStatus(token, userId, {
      status,
      reason: value || '后台手动调整'
    });
    ElNotification.success({
      title: '更新成功',
      message: `用户 ${userId} 已调整为 ${status}`
    });
    await loadUsers();
  } catch (error) {
    if (error === 'cancel' || error === 'close') {
      return;
    }
    showRequestError(error, '状态更新失败');
  }
}
</script>
