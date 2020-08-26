<template>
  <div class="in-wrap">
    <!-- 公共头部分 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="EduShop">
            <img src="~/assets/img/logo.png" width="100%" alt="EduShop">
          </a>
        </h1>
        <div class="h-r-nsl">
          <!-- 导航栏 -->
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <!-- todo: 功能待添加 -->
            <router-link to="/" tag="li" active-class="">
              <a>文章</a>
            </router-link>
            <router-link to="/" tag="li" active-class="">
              <a>问答</a>
            </router-link>
          </ul>
          <!-- 用户登录注册信息 -->
          <ul class="h-r-login">
            <!-- 未登录状态 -->
            <li v-if="!loginInfo.id" id="no-login">
              <a href="/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
            <!-- 已登陆状态 -->
            <li v-if="loginInfo.id" class="mr10" id="is-login-one">
              <!-- 消息数量 -->
              <a href="#" title="消息" id="headerMsgCountId">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>
            <li v-if="loginInfo.id" class="h-r-user" id="is-login-two">
              <!-- 用户头像 -->
              <a href="/ucenter" title>
                <img
                  :src="loginInfo.avatar"
                  width="30"
                  height="30"
                  class="vam picImg"
                  alt
                >
                <!-- 用户昵称 -->
                <span class="vam disIb" id="userName">{{ loginInfo.nickname }}</span>
              </a>
              <a href="javascript:void(0)" title="退出" @click="logout();" class="ml5">退出</a>
            </li>
          </ul>
          <!-- 搜索框 -->
          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>

    <!-- 中间nuxt部分 -->
    <nuxt/>

    <!-- 公共底部分 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="#" title="EduShop" target="_blank">EduShop在线教育</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_self">关于我们</a>|
                <a href="#" title="联系我们" target="_self">联系我们</a>|
                <a href="#" title="帮助中心" target="_self">帮助中心</a>|
                <a href="#" title="资源下载" target="_self">资源下载</a>|
                <span>mobile：13418186670</span>
                <span>Email：wty1793172997@163.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2020课程版权均归EduShop在线教育所有 京ICP备17055252号</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>

  </div>
</template>
<script>
  import "~/assets/css/reset.css"
  import "~/assets/css/theme.css"
  import "~/assets/css/global.css"
  import "~/assets/css/web.css"
  import '~/assets/css/base.css'
  import '~/assets/css/activity_tab.css'
  import '~/assets/css/bottom_rec.css'
  import '~/assets/css/nice_select.css'
  import '~/assets/css/order.css'
  import '~/assets/css/swiper-3.3.1.min.css'
  import "~/assets/css/pages-weixinpay.css"

  import cookie from 'js-cookie'
  import loginApi from '@/api/login'

  export default {
    data() {
      return {
        token: '',
        loginInfo: {
          id: '',
          age: '',
          avatar: '',
          mobile: '',
          nickname: '',
          sex: ''
        }
      }
    },

    created() {
      // 如果路径中包含token值则代表微信登陆
      this.token = this.$route.query.token
      console.log(this.token)
      if (this.token) {
        this.wxLogin()
      }
      // 显示用户信息
      this.showUserInfo()
    }, 

    methods: {
      // 微信登录
      wxLogin() {
        // 把token值放到cookie里面
        cookie.set('token', this.token, {domain: 'localhost'})
        cookie.set('userInfo', '', {domain: 'localhost'})
        // 根据token值获取用户信息
        loginApi.getUserInfo()
            .then(response => {
              // 保存用户信息
              this.loginInfo = response.data.userInfo
              cookie.set('userInfo', this.loginInfo, {domain: 'localhost'})
            })
      },

      // 如果cookie中包含用户信息则显示用户信息
      showUserInfo() {
        // 从cookie中获取用户信息
        let userStr = cookie.get('userInfo')
        // 把字符串转换成json对象
        if (userStr) {
          this.loginInfo = JSON.parse(userStr);
          console.log("loginInfo : ", this.loginInfo)
        }
      },

      // 退出登录
      logout() {
        // 清空cookie
        cookie.set('token', '', {domain: 'localhost'})
        cookie.set('userInfo', '', {domain: 'localhost'})
        // 返回首页
        window.location.href = '/'
      }
    }
  };
</script>

<style>
  .elideText {
    max-width: 80px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
</style>