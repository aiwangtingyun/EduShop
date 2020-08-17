<template>
  <div class="main">

    <!-- 头部登陆注册按钮 -->
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <!-- 登陆信息表单 -->
      <el-form ref="userForm" :model="user">
        <!-- 手机号 -->
        <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div >
            <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>
        <!-- 登陆按钮 -->
        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="http://qy.free.idcfengye.com/api/ucenter/weixinLogin/login"><i class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>

  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'

  import cookie from 'js-cookie'
  import loginApi from '@/api/login'

  export default {
    layout: 'sign',

    data() {
      return {
        //封装登录手机号和密码对象
        user:{
          mobile:'',
          password:''
        },
        //用户信息
        loginInfo:{}
      }
    },

    methods: {
      // 提交登陆
      submitLogin() {
        loginApi.userLogin(this.user)
          .then(response => {
            console.log(response)
            // 获取token并放到cookie中
            // 第一个参数为cookie名称，第二个为cookie值，第三个参数为cookie作用范围
            cookie.set('token', response.data.token, {domain: 'localhost'})
            // 根据token获取用户信息，用于首页显示
            loginApi.getUserInfo()
              .then(response => {
                this.loginInfo = response.data.userInfo
                // 把用户信息放到 cookie 中
                cookie.set('userInfo', this.loginInfo, {domain: 'localhost'})
                // 跳转到首页
                window.location.href = '/'
              })
          })
      },
      
      // 手机号码校验
      checkPhone(rule, value, callback) {
        if (!(/^1[34578]\d{9}$/.test(value))) {
          return callback(new Error('手机号码格式不正确'))
        }
        return callback()
      }
    }
  }
</script>

<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>