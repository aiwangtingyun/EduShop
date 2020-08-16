import request from '@/utils/request'

export default {
  // 获取首页名师和热门课程数据
  getIndexData() {
    return request({
      url: '/eduservice/indexfront/index',
      method: 'get'
    })
  }
}
