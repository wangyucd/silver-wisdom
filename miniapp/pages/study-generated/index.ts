import { fetchGeneratedLearn } from '../../services/api/course';
import { requireLogin } from '../../utils/auth';
import { formatDateTime } from '../../utils/format';

Page({
  data: {
    records: [] as Array<GeneratedLearnItem & { createdAtText: string }>
  },
  onShow() {
    if (!requireLogin()) {
      return;
    }
    void this.loadRecords();
  },
  async loadRecords() {
    const result = await fetchGeneratedLearn(1, 20);
    this.setData({
      records: result.list.map((item) => ({
        ...item,
        createdAtText: formatDateTime(item.createdAt)
      }))
    });
  }
});
