App<IAppOption>({
  globalData: {
    token: wx.getStorageSync('silver-wisdom-token') || '',
    userInfo: null
  },
  onLaunch() {
    const token = wx.getStorageSync('silver-wisdom-token');
    if (token) {
      this.globalData.token = token;
    }
  }
});
