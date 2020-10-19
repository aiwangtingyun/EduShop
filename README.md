# EduShop 

---

## 一、项目介绍

EduShop 是一个 B2C 模式的在线教育项目，分为**前台网站系统和后台运营平台**。

系统分为前台用户系统和后台管理系统两部分：

* 前台用户系统包括：首页、课程、名师、问答、文章。
* 后台管理系统包括：讲师管理、课程分类管理、课程管理、统计分析、Banner管理、订单管理、权限管理等功能。

项目**前后端分离**开发，后端采用 SpringCloud 微服务架构，持久层用的是 MyBatis-Plus，微服务分库设计，使用 Swagger 生成接口文档。

同时接入了阿里云视频点播、阿里云OSS，在业务中使用了 ECharts 做图表展示。

<br>

## 二、项目技术栈

前端使用的技术栈：**Node.js + Vue.js + Element-UI + NUXT + ECharts**

后端是一个使用 SpringBoot 搭建的分布式项目，分布式组件使用的是 Spring Cloud 和 Spring Cloud Alibaba ，整个项目分为：

* ACL 权限认证模块
* CMS 资源管理模块
* EDU 主业务模块
* MSM 短信服务模块
* ORDER 订单模块
* OSS 阿里云存储模块
* STATISTICS 数据统计模块
* UCENTER 用户管理模块
* VOD 阿里云视频点播服务模块

项目分布式组件：

* 使用 Spring Cloud Alibaba 的 Nacos 组件集群作为整个分布式的项目的**注册中心**和**配置中心**；
* 使用 OpenFeign 组件进行远程接口服务调用；
* 使用 Gateway 组件集群作为网关进行路由转发；
* 使用 Spring Security 作为权限认证中心；
* 使用 Spring Cloud Alibaba 的 Sentinel 组件处理服务降级、服务熔断和服务限流；
* 使用 Redis 集群作为缓存中间件存放数据，比如验证码、门户热门资源；
* 项目整合了 Swagger 组件作为后端接口的调试工具；
* 存储层使用的是 MySQL 集群；

第三方中间件：

* 使用阿里云的 OSS 存储服务；
* 使用阿里云的短信验证服务；
* 使用阿里云的视频点播服务；

整个项目使用 MyBatista-Plus 的逆向代码生工具来生成整个项目的业务逻辑代码，使用 Maven 作为项目打包管理工具，使用 Git 作为团队开发工具，并使用 Jenkins 进行项目自动化打包和部署。



微服务版使用 Nacos 作为注册中心，Spring Security 作为权限认证，使用 SpringCloud 中的 Gateway 作为网关解决跨域访问问题。

## 三、项目说明

为了便于开发和测试，项目中提供了单服务器版的功能模块，相当于把所有微服务模块集中到一起，对于用来学习以及想部署上线但又不想购买多台服务的小伙伴来说是非常方便的。

**具体的项目文档可以阅读 edu-doc 下的项目文档**。