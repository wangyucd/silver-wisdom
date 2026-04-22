import { saveOnboarding } from '../../services/api/user';
import { requireLogin } from '../../utils/auth';

Page({
  data: {
    tagOptions: ['养生', '慢病管理', '运动康复', '营养饮食', '心理陪伴', '智能设备'],
    selectedTagsMap: {} as Record<string, boolean>,
    submitting: false
  },
  onShow() {
    requireLogin();
  },
  toggleTag(event: WechatMiniprogram.TouchEvent) {
    const tag = String(event.currentTarget.dataset.tag);
    const nextSelected = { ...this.data.selectedTagsMap };
    nextSelected[tag] = !nextSelected[tag];
    this.setData({ selectedTagsMap: nextSelected });
  },
  async handleSubmit() {
    const tags = Object.entries(this.data.selectedTagsMap)
      .filter(([, selected]) => selected)
      .map(([tag]) => tag);
    if (!tags.length) {
      wx.showToast({ title: '请至少选择一个标签', icon: 'none' });
      return;
    }

    this.setData({ submitting: true });
    try {
      await saveOnboarding(tags, 'MINIAPP_ONBOARDING');
      wx.showToast({ title: '画像已保存', icon: 'success' });
      wx.switchTab({ url: '/pages/home/index' });
    } finally {
      this.setData({ submitting: false });
    }
  }
});
