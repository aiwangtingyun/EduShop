import axios from 'axios'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:9001', // api的base_url
  timeout: 20000 // 请求超时时间
})

// response 拦截器
service.interceptors.response.use(
  response => {
    // 对数据进行封装
    const res = response.data
    if (res.code === 20000) {
      return response.data
    }
  }
)

export default service
