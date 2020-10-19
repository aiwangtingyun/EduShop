
# EduShop 微服务版文档

---

### 微服务版涉及的目录包括：

```bash
/---	
 |--admin-ui	
 |--front-ui	
 |--common		
 	|--common_utils		
 	|--service_base		
 	|--spring_security	
 |--infrastuctrue	 
 	|--canal_client	 
 	|--gateway		 
 |--service_cloud	 		
    |--service_acl			
    |--service_cms			
    |--service_edu			
    |--service_msm			
    |--service_order		
    |--service_oss			
    |--service_statistics	 
    |--service_ucenter		
    |--service_vod		
 |--sql		
```

### 目录结构说明：

### admin-ui 

admin-ui 为后台管理前端UI，使用的是 vue-admin-template 前端框架，package.json 已经包含了所有依赖包，安装好 nodejs 之后，直接命令 **npm install** 安装依赖模块。

在 config 目录下，包含了框架的配置文件，比如 dev 模式的配置文件 dev.env.js 内容为：

```js
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"http://localhost:9001/"',
})
```

该文件配置了后端接口的请求地址为 http://localhost:9001/ ，然后在 utils 目录下的 request.js 中引用该地址：

```js
// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5000 // 请求超时时间
})
```

由于所有的 API 接口都是使用 axios 异步请求，所以，所有后端api请求都会使用该地址。

整个框架的核心代码在 src 目录下，该目录下定义项目中使用的所有 api 接口，views 页面试图，同时也在 utils 目录下定义了刚才提到的 axios 请求的封装模块 request.js，同时 request.js 模块还对 axios 请求进行请求和响应的拦截处理以及响应数据的处理。

启动项目只需在命令行中输入：

```bash
npm run dev
```

### front-ui

front-ui 为前台系统的前端UI，使用的是 NUXT 服务端渲染框架，使用 NUXT 的好处当然是为了 SEO。和 admin-ui 一样，我们需要在该目录下使用 npm install 安装依赖模块。和 admin-ui 不同的是，front-ui 直接在 utils 目录下 request.js 中直接封装了访问后端api接口的地址：

```js
// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:9001', // api的base_url
  timeout: 20000 // 请求超时时间
})
```

所以如果要更改后台api的访问地址需要在这里修改。

页面视图部分 nuxt 分为了 layouts 目录和 pages 目录，layouts 中抽离了每个页面中的共同部分，比如头部和尾部的页面内容，而中间部分的页面则放在了 pages 中。

nuxt 默认启动地址端口为：http://localhost:3000/ ，如果想要修改的话需要在 package.json 中添加配置：

```json
"config": {
    "nuxt": {
      "host": "0.0.0.0",
      "port": "2233"
    }
}
```

在 plugins 目录下包含了自定义的插件，比如幻灯片插件 swipper，引入自定义插件需要在 nuxt.config.js 中引入，比如引入 swipper 插件：

```js
// plugins
plugins: [
    {src: '~/plugins/nuxt-swiper-plugin.js', ssr: false}
],
// css
css: [
    'swiper/swiper-bundle.css'
],
```

### common

common 模块为后端服务模块的通用模块包含了 service_clound 下所有子模块中需要用到共同工具、模块等。因为所有模块都用到了 spring_security 进行权限认证，所以这个模块也放在了 common 模块下。

### infrastructure 

infrastructure 模块为基础设施模块，里面包含了数据同步模块 canal_client 模块，用于解决跨域的 gateway 模块。由于是多模块服务，每个模块都有自己的端口号，所以存在服务代理转发的问题。解决代理问题可以使用 nginx 来实现，但使用 gateway 既可以实现代理转发也可以解决跨域问题。

所以如果开启 gateway 模块的话，在每个微服务模块中的 controller 层就不要使用 @CrossOrigin 注解了，因为这两个作用是相同的，如果两个都一起使用相当于又跨回去了。

**另外，需要注意的是 gateway 模块也有自己的端口号，如果开启了该模块，则需要在前端框架中修改后端接口访问的端口号为 gateway 所对应的端口号。**

### service_cloud

service_cloud 模块就是所有微服务功能模块了：

* service_acl 为权限认证模块，配合 spring_security 模块一起使用
* service_cms 为资源管理模块，比如管理前端页面显示的 banner 资源列表
* service_edu 为项目的业务模块，包括管理讲师、课程等
* service_msm 为阿里云短信服务模块，用于用户短信注册
* service_order 为订单模块，负责管理用户的课程支付和购买
* service_oss 为阿里云oss服务模块，用于存储用户头像、讲师头像、课程封面等资源
* service_statistics 为数据统计模块，管理数据统计信息
* service_ucenter 为用户管理模块，比如管理用户登录注册等
* service_vod 为阿里云视频点播服务模块，提供阿里云视频的上传、转码、认证等功能

**注意**：

在 service_ucenter 模块中的配置文件的微信登录配置：

```properties
# 微信 id
wx.open.app_id=wxed9954c01bb89b47
# 微信密钥
wx.open.app_secret=a7482517235173ddb4083788de60b90e
# 授权登录回调地址
wx.open.redirect_url=http://guli.shop/api/ucenter/wx/callback
```

这三个参数都是谷粒学院提供的，特别是这个回调地址域名，谷粒学院为了方便大家测试，使用程序对这个域名地址做了特殊处理，使域名跳转到 http://localhost:8150 这个域名地址中。网址的固定端口为 8150，所以为了使微信登录能够回调我们接口，我们可以把 service_ucenter 模块的端口修改为 8150，或者我们可以借助 nginx 来监听 8150 端口，然后转发到我们自己的实际统一调用的端口中。

**下面是 Nginx 代理的配置：**

```nginx
# 注意：最好修改默认的 80端口到81
http {

    # 设置上传文件大小
    client_max_body_size 1024m;

    # 修改监听端口
    server {
        listen 81;
        ......
    }，
    
    ......
    
    # 微信回调 8150 端口转发到统一的 9001 接口
    server {
        listen          8150;
        server_name     localhost;

        location / {
            proxy_pass  http://localhost:9001;
        }
        
    }

    # 后台服务统一调用端口
    server {
        # 监听 9001 端口
        listen       9001;
        server_name  localhost;

        # edu 模块端口
        location ~ /eduservice/ {
            proxy_pass http://localhost:8001;
        }

        # oss 模块端口
        location ~ /eduoss/ {
            proxy_pass http://localhost:8002;
        }
        
        # vod 模块端口
        location ~ /eduvod/ {           
            proxy_pass http://localhost:8003;
        }
        
        # cms 模块端口
        location ~ /educms/ {           
            proxy_pass http://localhost:8004;
        }
        
        # msm 模块端口
        location ~ /edumsm/ {           
            proxy_pass http://localhost:8005;
        }
        
        # ucenter 模块端口
        location ~ /educenter/ {           
            proxy_pass http://localhost:8006;
        }
        
        # order 模块端口
        location ~ /eduorder/ {           
            proxy_pass http://localhost:8007;
        }
        
        # statistics 模块端口
        location ~ /edustatistics/ {
            proxy_pass http://localhost:8008;
        }
        
        # acl 模块端口
        location ~ /aclservice/ {
            proxy_pass http://localhost:8009;
        }
    }
}
```

## 项目启动说明

### 启动要求

* 由于项目中使用了 redis 和 nacos 模块，所以在启动项目之前需要启动这两个服务
* 开启 Nginx 服务代理
* 如果是第一次使用该项目，则需要执行 sql 目录下的所有 sql 数据库文件，里面包含了项目的所有数据表。数据库使用的是 edushop，所以需要在 mysql 中创建这个数据库，然后在所有表都创建在这个数据库中
* 如果需要用到阿里云的 OSS 和 VOD 服务，需要在 .properties 配置文件中配 **keyid** 和 **keysecret**

### 项目启动配置问题

如果修改了前端 nuxt 框架的启动端口号，需要在 ucenter 模块下的 WxApiController.java 中修改微信登录成功回调接口的重定向地址的端口：

```java
// 返回首页面并通过路径传递token字符串
return "redirect:http://localhost:3000?token=" + jwtToken;
```

从中可以看出，微信登录成功后，后端做的处理是跳转到前端页面首页，所以这个前端页面的IP和端口必须保持一致，否则会包页面找不到。