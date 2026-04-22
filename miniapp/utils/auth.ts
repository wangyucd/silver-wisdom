export function getToken() {
  return wx.getStorageSync('silver-wisdom-token') || '';
}

export function setToken(token: string) {
  wx.setStorageSync('silver-wisdom-token', token);
  getApp<IAppOption>().globalData.token = token;
}

export function clearToken() {
  wx.removeStorageSync('silver-wisdom-token');
  getApp<IAppOption>().globalData.token = '';
}

export function requireLogin() {
  if (getToken()) {
    return true;
  }
  wx.navigateTo({ url: '/pages/login/index' });
  return false;
}
