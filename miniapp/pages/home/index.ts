import { fetchCategories, fetchContentPage, fetchHotspots } from '../../services/api/content';
import { buildTags } from '../../utils/format';

Page({
  data: {
    hotspots: [] as HotspotEntity[],
    categories: [] as CategoryEntity[],
    contents: [] as Array<ContentItemEntity & { tagText: string }>,
    activeCategoryId: 0
  },
  onLoad() {
    void this.loadPage();
  },
  onPullDownRefresh() {
    this.loadPage().finally(() => wx.stopPullDownRefresh());
  },
  async loadPage() {
    const [hotspots, categories] = await Promise.all([fetchHotspots(), fetchCategories()]);
    this.setData({
      hotspots,
      categories
    });
    await this.loadContents(this.data.activeCategoryId);
  },
  async loadContents(categoryId?: number) {
    const result = await fetchContentPage({
      categoryId: categoryId || undefined,
      sortBy: 'LATEST',
      pageNum: 1,
      pageSize: 20
    });
    this.setData({
      contents: result.list.map((item) => ({
        ...item,
        tagText: buildTags(item.tags || [])
      }))
    });
  },
  async handleCategoryChange(event: WechatMiniprogram.TouchEvent) {
    const categoryId = Number(event.currentTarget.dataset.id || 0);
    this.setData({ activeCategoryId: categoryId });
    await this.loadContents(categoryId);
  }
});
