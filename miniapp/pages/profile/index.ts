import { fetchCurrentUser, logout } from '../../services/api/auth';
import { fetchCurrentTags } from '../../services/api/user';
import { clearToken, requireLogin } from '../../utils/auth';

Page({
  data: {
    profile: {} as Partial<UserProfile>,
    tags: [] as UserInterestTag[]
  },
  onShow() {
    if (!requireLogin()) {
      return;
    }
    void this.loadProfile();
  },
  async loadProfile() {
    const [profile, tagResponse] = await Promise.all([fetchCurrentUser(), fetchCurrentTags()]);
    this.setData({
      profile,
      tags: tagResponse.tags || []
    });
  },
  async handleLogout() {
    try {
      await logout();
    } catch {
      // 本地清理优先
    }
    clearToken();
    wx.reLaunch({ url: '/pages/login/index' });
  }
});
