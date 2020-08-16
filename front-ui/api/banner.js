import request from '@/utils/request'

export default {
  // 获取banner列表
  getBannerList() {
    return request({
      url: '/educms/bannerfront/getBannerList',
      method: 'get'
    })
  }
}
