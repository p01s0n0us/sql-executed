const fetch = require('../../utils/fetch')
const app = getApp()

Page({

  data: {
    id: null,
    question: {},
    openid: null
  },

  loadQuestion: function(id) {
    return fetch(`/question/${this.data.id}`, {
      openid: this.data.openid
    }).then(res => {
      console.log('question: ', res.data)
      this.setData({
        question: res.data
      })
    })
  },

  submit: function(e) {
    let sql = e.detail.value.sql
    if (sql == null || sql == '') {
      wx.showToast({
        title: '要么写完，要么quit', //提示文字
        duration: 1000, //显示时长
        mask: true, //是否显示透明蒙层，防止触摸穿透，默认：false  
        icon: 'none'
      })
    } else {
      fetch('/validate', {
        id: this.data.question.id,
        type: this.data.question.type,
        sql: sql,
        openid: this.data.openid
      }, 'POST').then(res=>{
        let correct = res.data.data
        if(correct){
          wx.showToast({
            title: '正确', //提示文字
            duration: 2000, //显示时长
            mask: true, //是否显示透明蒙层，防止触摸穿透，默认：false  
            image: '/pics/success.png',     
            success: function () {
              setTimeout(function () {
                //要延时执行的代码
                wx.switchTab({
                  url: '/pages/index/index',
                })
              }, 2000) //延迟时间
            }
          })
        }else{
          wx.showToast({
            title: '错误', //提示文字
            duration: 2000, //显示时长
            mask: true, //是否显示透明蒙层，防止触摸穿透，默认：false  
            image: '/pics/fail.png'    
          })          
        }
      })
    }
  },

  onLoad: function(options) {
    this.setData({
      id: options.id
    })
    if (app.globalData.openid) {
      this.setData({
        openid: app.globalData.openid
      })
      this.loadQuestion()
    } else {
      app.openidReadyCallback = res => {
        this.setData({
          openid: res.data.openid
        })
        this.loadQuestion()
      }
    }
  }
})