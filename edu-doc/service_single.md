
# EduShop 单服务器版文档

---

项目的功能介绍请阅读微服务版项目文档 service_cloud.md。

单服务版和微服务版的唯一区别是，把所有服务模块整合到一起，不用区分共有模块和子模块。所以不影响要 Nacos 服务，不需要 Feign 的远程调用，只有一个统一的 9001 服务端口号。

**所以需要 Nginx 配置如下：**

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

}
```

由于服务端口号就是前端调用的端口号，所以不需要进行服务模块转发了，除了需要添加微信登录要用到的 8150 端口转发。其它操作和微服务版大致相同：

* 需要开启 Redis 服务，不需要 Nacos 服务了
* 开启 Nginx 服务代理
* 数据库该导入的导入
* 前端调用接口和后端保持一致
* 如果需要用到阿里云的 OSS 和 VOD 服务，需要在 .properties 配置文件中配 **keyid** 和 **keysecret**