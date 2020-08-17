# EduShop

## Nginx 配置
后台管理的前端访问的端口统一为 9001 ：

```javascript
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

    # 设置上传文件大小
    client_max_body_size 1024m;

    # 修改监听端口
    server {
        listen 81;
        ......
    }，
    
    ......

    server {
        # 监听 9001 端口
        listen       9001;
        server_name  localhost;

        location ~ /eduservice/ {
            proxy_pass http://localhost:8001;
        }

        location ~ /eduoss/ {
            proxy_pass http://localhost:8002;
        }
        
        location ~ /eduvod/ {           
            proxy_pass http://localhost:8003;
        }
        
        location ~ /educms/ {           
            proxy_pass http://localhost:8004;
        }
        
        location ~ /edumsm/ {           
            proxy_pass http://localhost:8005;
        }
        
        location ~ /educenter/ {           
            proxy_pass http://localhost:8150;
        }
    }
}
```

