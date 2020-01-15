App({
  config: {
    apiBase: 'http://47.112.27.55:35635'
    // apiBase: 'http://localhost:35635'
  },

  globalData: {
    userInfo: null,
    openid: null
  },

  onLaunch: function() {
    wx.getSetting({
      success: res => {
        console.log('wx.getSetting: ', res)
        if (res.authSetting['scope.userInfo']) {
          wx.getUserInfo({
            success: res => {
              console.log('wx.getUserInfo: ', res)
              this.globalData.userInfo = res.userInfo
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })

    wx.login({
      success: res => {
        wx.request({
          url: this.config.apiBase + '/user/login',
          data: {
            code: res.code
          },
          success: res => {
            console.log('wx.request: ', res)
            this.globalData.openid = res.data.openid
            if (this.openidReadyCallback) {
              this.openidReadyCallback(res)
            }
          }
        })
      }
    })
  }
})