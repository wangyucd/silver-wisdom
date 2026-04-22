import { chatWithAi, fetchGenerateTask, generateByAi } from '../../services/api/content';
import { requireLogin } from '../../utils/auth';

Page({
  data: {
    question: '',
    prompt: '',
    scene: 'HEALTH_PLAN',
    chatting: false,
    generating: false,
    chatAnswer: '',
    references: [] as ReferenceResponse[],
    task: {} as Partial<AiGenerateTaskResponse>
  },
  onShow() {
    requireLogin();
  },
  handleQuestionInput(event: WechatMiniprogram.Input) {
    this.setData({ question: event.detail.value });
  },
  handlePromptInput(event: WechatMiniprogram.Input) {
    this.setData({ prompt: event.detail.value });
  },
  handleSceneInput(event: WechatMiniprogram.Input) {
    this.setData({ scene: event.detail.value });
  },
  async handleChat() {
    if (!this.data.question.trim()) {
      wx.showToast({ title: '请输入问题', icon: 'none' });
      return;
    }
    this.setData({ chatting: true });
    try {
      const result = await chatWithAi(this.data.question, []);
      this.setData({
        chatAnswer: result.answer,
        references: result.references || []
      });
    } finally {
      this.setData({ chatting: false });
    }
  },
  async handleGenerate() {
    if (!this.data.prompt.trim()) {
      wx.showToast({ title: '请输入提示词', icon: 'none' });
      return;
    }
    this.setData({ generating: true });
    try {
      const task = await generateByAi(this.data.prompt, this.data.scene || 'HEALTH_PLAN');
      this.setData({ task });
      if (task.taskId) {
        setTimeout(async () => {
          const latestTask = await fetchGenerateTask(task.taskId);
          this.setData({ task: latestTask });
        }, 1800);
      }
    } finally {
      this.setData({ generating: false });
    }
  }
});
