const fetch = require('../../utils/fetch')
const app = getApp()

Page({
  data: {
    recordItem: [],
    pageIndex: 0,
    pageSize: 20,
    totalCount: 0,
    hasMore: true,
    openid: null
  },

  loadMore() {
    let pageIndex = this.data.pageIndex

    return fetch(`/record/page/${pageIndex++}`, {
        openid: this.data.openid
      })
      .then(res => {
        console.log('recordItem: ', res.data)
        const recordItem = this.data.recordItem.concat(res.data)
        const totalCount = parseInt(res.header['Total-Count'])
        const hasMore = pageIndex * this.data.pageSize < totalCount
        this.setData({
          recordItem,
          pageIndex,
          totalCount,
          hasMore
        })
      })
  },


  onLoad: function(options) {
    if (app.globalData.openid) {
      this.setData({
        openid: app.globalData.openid
      })
      this.loadMore()
    } else {
      app.openidReadyCallback = res => {
        this.setData({
          openid: res.data.openid
        })
        this.loadMore()
      }
    }
  },

  onPullDownRefresh() {
    this.setData({
      recordItem: [],
      pageIndex: 0,
      hasMore: true
    })
    this.loadMore().then(() => wx.stopPullDownRefresh())
  },

  onReachBottom() {
    if (this.data.hasMore) {
      this.loadMore()
    }
  },
})