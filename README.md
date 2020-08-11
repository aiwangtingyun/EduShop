# EduShop

## Nginx 配置
后台管理的前端访问的端口统一为 9001 ：

```js
module.exports = merge(prodEnv, {
  ......
  BASE_API: '"http://localhost:9001/"',
})
```

而后端的服务拆分为两个：

* 业务处理使用 8001 端口
* 阿里云 OSS 云存储上传使用 8002 端口

所以需要配置 Nginx 实现代理转发功能：

```python
# 注意：最好修改默认的 80端口到81
http {
    server {
        listen 81;
        ......
    }，
    
    ......

    server {
        # 监听 9001 端口
        listen       9001;
        server_name  localhost;

        # 如果访问路径包含 /eduservice/ 则转发到 8001 端口中处理业务逻辑
        location ~ /eduservice/ {
            proxy_pass http://localhost:8001;
        }

        # 如果访问路径包含 /eduoss/ 则转发到 8002 端口中处理OSS上传逻辑
        location ~ /eduoss/ {
            proxy_pass http://localhost:8002;
        }
    }
}
```

