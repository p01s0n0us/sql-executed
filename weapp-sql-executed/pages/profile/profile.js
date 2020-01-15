const fetch = require('../../utils/fetch')
const app = getApp()

Page({
  data: {
    openid: null,
    userInfo: null,
    hasUserInfo: false
  },

  onLoad: function() {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else {
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    }

    if (app.globalData.openid) {
      this.setData({
        openid: app.globalData.openid
      })
    } else {
      app.openidReadyCallback = res => {
        this.setData({
          openid: res.data.openid
        })
      }
    }
  },

  getUserInfo: function(e) {
    console.log('getUserInfo: ', e)
    if (e.detail.errMsg == 'getUserInfo:ok') {
      this.setData({
        userInfo: e.detail.userInfo,
        hasUserInfo: true
      })
    }
  }
})