<template>
  <div class="min-h-screen bg-[linear-gradient(180deg,#eef4ff_0%,#f8fbff_35%,#f4f0ea_100%)] text-brand-900">
    <div class="grid min-h-screen grid-cols-[264px,1fr]">
      <aside class="flex flex-col justify-between bg-[linear-gradient(180deg,#123866_0%,#205493_100%)] px-5 py-7 text-white">
        <div class="space-y-8">
          <div class="space-y-3">
            <div class="h-13 w-13 flex items-center justify-center rounded-2xl bg-white/18 text-lg font-700 tracking-wide">
              SW
            </div>
            <div>
              <h1 class="text-2xl font-700">Silver Wisdom</h1>
              <p class="text-sm text-white/76">后台运营台</p>
            </div>
          </div>

          <el-menu
            :default-active="activeMenu"
            router
            class="!border-none !bg-transparent"
            text-color="rgba(255,255,255,.78)"
            active-text-color="#fff"
          >
            <el-menu-item index="/">
              <span>运营看板</span>
            </el-menu-item>
            <el-menu-item index="/users">
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="/content">
              <span>内容管理</span>
            </el-menu-item>
          </el-menu>
        </div>

        <el-button plain class="!bg-white/10 !text-white !border-white/20" @click="handleLogout">
          退出登录
        </el-button>
      </aside>

      <main class="flex flex-col gap-5 p-7">
        <header class="rounded-3xl border border-white/65 bg-white/78 px-6 py-5 shadow-[0_24px_60px_rgba(32,84,147,0.12)] backdrop-blur">
          <div class="flex items-center justify-between">
            <div>
              <h2 class="text-xl font-700 text-brand-900">{{ profile?.name || '管理员' }}</h2>
              <p class="mt-1 text-sm text-brand-700/70">{{ profile?.username || '未获取账号信息' }}</p>
            </div>
            <el-tag type="primary" effect="plain">{{ profile?.status || 'UNKNOWN' }}</el-tag>
          </div>
        </header>

        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessageBox } from 'element-plus';
import { useAdminSession } from '../composables/useAdminSession';

const route = useRoute();
const router = useRouter();
const { profile, refreshProfile, signOut } = useAdminSession();

const activeMenu = computed(() => {
  if (route.path.startsWith('/users')) {
    return '/users';
  }
  if (route.path.startsWith('/content')) {
    return '/content';
  }
  return '/';
});

onMounted(() => {
  void refreshProfile();
});

async function handleLogout() {
  try {
    await ElMessageBox.confirm('确认退出当前管理员账号吗？', '退出登录', {
      type: 'warning'
    });
    await signOut();
    await router.push('/login');
  } catch {
    // 用户取消时不做额外处理
  }
}
</script>
