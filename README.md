# EduShop 

---

## 项目简介

EduShop 是一个 B2C 模式的在线教育项目，分为**前台网站系统和后台运营平台**。

系统分为前台用户系统和后台管理系统两部分：

* 前台用户系统包括：首页、课程、名师、问答、文章。
* 后台管理系统包括：讲师管理、课程分类管理、课程管理、统计分析、Banner管理、订单管理、权限管理等功能。

项目**前后端分离**开发，后端采用 SpringCloud 微服务架构，持久层用的是 MyBatis-Plus，微服务分库设计，使用 Swagger 生成接口文档。

同时接入了阿里云视频点播、阿里云OSS，在业务中使用了 ECharts 做图表展示。

## 项目技术栈

前端的架构是：**Node.js + Vue.js + element-ui + NUXT + ECharts**

后端的主要技术架构是：**SpringBoot + SpringCloud + MyBatis-Plus + HttpClient + MySQL + Maven + EasyExcel + nginx**

其他涉及到的中间件包括 Redis、阿里云OSS、阿里云视频点播，使用 ECharts 做图表展示，使用 EasyExcel 完成分类批量添加、注册分布式单点登录使用了JWT。

微服务版使用 Nacos 作为注册中心，Spring Security 作为权限认证，使用 SpringCloud 中的 Gateway 作为网关解决跨域访问问题。

## 项目说明

为了便于开发和测试，项目中提供了单服务器版的功能模块，相当于把所有微服务模块集中到一起，对于用来学习以及想部署上线但又不想购买多台服务的小伙伴来说是非常方便的。

**具体的项目文档可以阅读 edu-doc 下的项目文档**。