<template>
  <section class="space-y-5">
    <el-card shadow="never" class="rounded-[24px] !border-white/70">
      <template #header>
        <div>
          <div class="text-lg font-700 text-brand-900">内容运营</div>
          <div class="text-sm text-brand-700/70">
            覆盖 `/content/admin/*` 写接口与 `/content/categories` 公共查询接口
          </div>
        </div>
      </template>

      <el-tabs v-model="activeTab" class="admin-tabs">
        <el-tab-pane label="热点配置" name="hotspots">
          <div class="grid grid-cols-[420px,1fr] gap-5">
            <el-card shadow="never" class="rounded-[20px]">
              <template #header>
                <div class="font-700 text-brand-900">新增热点</div>
              </template>
              <el-form label-position="top">
                <el-form-item label="标题">
                  <el-input v-model="hotspotForm.title" />
                </el-form-item>
                <el-form-item label="摘要">
                  <el-input v-model="hotspotForm.summary" type="textarea" :rows="4" />
                </el-form-item>
                <el-form-item label="封面地址">
                  <el-input v-model="hotspotForm.coverUrl" />
                </el-form-item>
                <el-form-item label="权重">
                  <el-input-number v-model="hotspotForm.weight" :min="1" :max="999" class="w-full" />
                </el-form-item>
                <el-button type="primary" @click="submitHotspot">保存热点</el-button>
              </el-form>
            </el-card>

            <el-table :data="hotspots" border>
              <el-table-column label="标题" prop="title" min-width="180" />
              <el-table-column label="摘要" prop="summary" min-width="240" />
              <el-table-column label="权重" prop="weight" width="100" />
            </el-table>
          </div>
        </el-tab-pane>

        <el-tab-pane label="分类配置" name="categories">
          <div class="grid grid-cols-[420px,1fr] gap-5">
            <el-card shadow="never" class="rounded-[20px]">
              <template #header>
                <div class="font-700 text-brand-900">新增分类</div>
              </template>
              <el-form label-position="top">
                <el-form-item label="名称">
                  <el-input v-model="categoryForm.name" />
                </el-form-item>
                <el-form-item label="图标地址">
                  <el-input v-model="categoryForm.iconUrl" />
                </el-form-item>
                <el-form-item label="封面地址">
                  <el-input v-model="categoryForm.coverUrl" />
                </el-form-item>
                <el-form-item label="排序">
                  <el-input-number v-model="categoryForm.sort" :min="1" :max="999" class="w-full" />
                </el-form-item>
                <el-button type="primary" @click="submitCategory">保存分类</el-button>
              </el-form>
            </el-card>

            <el-table :data="categories" border>
              <el-table-column label="ID" prop="id" width="80" />
              <el-table-column label="名称" prop="name" min-width="160" />
              <el-table-column label="排序" prop="sort" width="100" />
              <el-table-column label="状态" prop="status" width="100" />
            </el-table>
          </div>
        </el-tab-pane>

        <el-tab-pane label="内容配置" name="contents">
          <div class="grid grid-cols-[480px,1fr] gap-5">
            <el-card shadow="never" class="rounded-[20px]">
              <template #header>
                <div class="font-700 text-brand-900">新增内容</div>
              </template>
              <el-form label-position="top">
                <el-form-item label="标题">
                  <el-input v-model="contentForm.title" />
                </el-form-item>
                <el-form-item label="摘要">
                  <el-input v-model="contentForm.summary" type="textarea" :rows="3" />
                </el-form-item>
                <el-form-item label="类型">
                  <el-select v-model="contentForm.type" class="w-full">
                    <el-option label="ARTICLE" value="ARTICLE" />
                    <el-option label="VIDEO" value="VIDEO" />
                  </el-select>
                </el-form-item>
                <el-form-item label="分类">
                  <el-select v-model="contentForm.categoryId" class="w-full">
                    <el-option
                      v-for="item in categories"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="封面地址">
                  <el-input v-model="contentForm.coverUrl" />
                </el-form-item>
                <el-form-item label="视频地址">
                  <el-input v-model="contentForm.videoUrl" />
                </el-form-item>
                <el-form-item label="热度">
                  <el-input-number v-model="contentForm.heatScore" :min="0" :max="9999" class="w-full" />
                </el-form-item>
                <el-form-item label="标签">
                  <el-input v-model="tagInput" placeholder="用逗号分隔标签" />
                </el-form-item>
                <el-form-item label="正文">
                  <el-input v-model="contentForm.contentBody" type="textarea" :rows="6" />
                </el-form-item>
                <el-button type="primary" @click="submitContent">保存内容</el-button>
              </el-form>
            </el-card>

            <el-table :data="contents" border>
              <el-table-column label="ID" prop="id" width="80" />
              <el-table-column label="标题" prop="title" min-width="200" />
              <el-table-column label="分类" min-width="120">
                <template #default="{ row }">
                  {{ categoryNameMap.get(row.categoryId) || row.categoryId || '--' }}
                </template>
              </el-table-column>
              <el-table-column label="标签" min-width="180">
                <template #default="{ row }">
                  {{ joinTags(row.tags) }}
                </template>
              </el-table-column>
              <el-table-column label="热度" prop="heatScore" width="100" />
              <el-table-column label="发布时间" min-width="180">
                <template #default="{ row }">
                  {{ formatDateTime(row.publishTime) }}
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue';
import { ElNotification } from 'element-plus';
import {
  fetchAdminContents,
  fetchCategories,
  fetchHotspots,
  saveCategory,
  saveContent,
  saveHotspot
} from '../api/content';
import { getAdminToken } from '../stores/auth';
import type {
  CategoryEntity,
  CategoryUpsertRequest,
  ContentItemEntity,
  ContentUpsertRequest,
  HotspotEntity,
  HotspotUpsertRequest
} from '../types/domain';
import { formatDateTime, joinTags } from '../utils/format';
import { showRequestError } from '../utils/message';

const activeTab = ref('hotspots');
const hotspots = ref<HotspotEntity[]>([]);
const categories = ref<CategoryEntity[]>([]);
const contents = ref<ContentItemEntity[]>([]);
const tagInput = ref('');

const hotspotForm = reactive<HotspotUpsertRequest>({
  title: '',
  summary: '',
  coverUrl: '',
  weight: 10
});

const categoryForm = reactive<CategoryUpsertRequest>({
  name: '',
  iconUrl: '',
  coverUrl: '',
  sort: 10
});

const contentForm = reactive<ContentUpsertRequest>({
  title: '',
  summary: '',
  coverUrl: '',
  type: 'ARTICLE',
  contentBody: '',
  videoUrl: '',
  categoryId: 0,
  heatScore: 50,
  tags: []
});

const categoryNameMap = computed(() => new Map(categories.value.map((item) => [item.id, item.name])));

onMounted(() => {
  void loadAll();
});

async function loadAll() {
  const token = getAdminToken();
  if (!token) {
    return;
  }
  try {
    const [nextHotspots, nextCategories, nextContents] = await Promise.all([
      fetchHotspots(token),
      fetchCategories(token),
      fetchAdminContents(token)
    ]);
    hotspots.value = nextHotspots;
    categories.value = nextCategories;
    contents.value = nextContents.map((item) => ({
      ...item,
      categoryId: item.categoryId ?? 0
    }));
    if (nextCategories.length && !contentForm.categoryId) {
      contentForm.categoryId = nextCategories[0].id || 0;
    }
  } catch (error) {
    showRequestError(error, '内容配置加载失败');
  }
}

function normalizeTags() {
  return tagInput.value
    .split(/[\n,，]/)
    .map((item) => item.trim())
    .filter(Boolean);
}

async function submitHotspot() {
  const token = getAdminToken();
  if (!token) {
    return;
  }
  try {
    await saveHotspot(token, { ...hotspotForm });
    ElNotification.success({ title: '保存成功', message: '热点已写入后台' });
    Object.assign(hotspotForm, { title: '', summary: '', coverUrl: '', weight: 10 });
    await loadAll();
  } catch (error) {
    showRequestError(error, '热点保存失败');
  }
}

async function submitCategory() {
  const token = getAdminToken();
  if (!token) {
    return;
  }
  try {
    await saveCategory(token, { ...categoryForm });
    ElNotification.success({ title: '保存成功', message: '分类已写入后台' });
    Object.assign(categoryForm, { name: '', iconUrl: '', coverUrl: '', sort: 10 });
    await loadAll();
  } catch (error) {
    showRequestError(error, '分类保存失败');
  }
}

async function submitContent() {
  const token = getAdminToken();
  if (!token) {
    return;
  }
  try {
    await saveContent(token, {
      ...contentForm,
      tags: normalizeTags()
    });
    ElNotification.success({ title: '保存成功', message: '内容已写入后台' });
    Object.assign(contentForm, {
      title: '',
      summary: '',
      coverUrl: '',
      type: 'ARTICLE',
      contentBody: '',
      videoUrl: '',
      categoryId: categories.value[0]?.id || 0,
      heatScore: 50,
      tags: []
    });
    tagInput.value = '';
    await loadAll();
  } catch (error) {
    showRequestError(error, '内容保存失败');
  }
}
</script>
