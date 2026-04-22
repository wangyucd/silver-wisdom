import { wxLogin } from '../../services/api/auth';
import { setToken } from '../../utils/auth';

Page({
  data: {
    code: '',
    submitting: false
  },
  handleCodeInput(event: WechatMiniprogram.Input) {
    this.setData({ code: event.detail.value });
  },
  async handleLogin() {
    const debugCode = this.data.code || `debug-${Date.now()}`;
    this.setData({ submitting: true });
    try {
      const result = await wxLogin(debugCode);
      setToken(result.token);
      wx.showToast({ title: '登录成功', icon: 'success' });
      if (result.newUser) {
        wx.redirectTo({ url: '/pages/onboarding/index' });
        return;
      }
      wx.switchTab({ url: '/pages/home/index' });
    } finally {
      this.setData({ submitting: false });
    }
  }
});
