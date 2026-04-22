import { computed, ref } from 'vue';
import type { AdminProfileResponse } from '../types/domain';
import { getAdminToken, loadAdminProfile, logoutAdmin } from '../stores/auth';

const profileRef = ref<AdminProfileResponse | null>(null);
const loadingRef = ref(false);

export function useAdminSession() {
  const token = computed(() => getAdminToken());

  async function refreshProfile() {
    loadingRef.value = true;
    profileRef.value = await loadAdminProfile();
    loadingRef.value = false;
  }

  async function signOut() {
    await logoutAdmin();
    profileRef.value = null;
  }

  return {
    token,
    profile: profileRef,
    loading: loadingRef,
    refreshProfile,
    signOut
  };
}
