import { fetchContentDetail } from '../../services/api/content';

Page({
  data: {
    detail: {} as Partial<ContentItemEntity>
  },
  async onLoad(query: Record<string, string>) {
    const contentId = Number(query.contentId || 0);
    if (!contentId) {
      wx.showToast({ title: '缺少内容编号', icon: 'none' });
      return;
    }
    const detail = await fetchContentDetail(contentId);
    this.setData({ detail });
  }
});
