import request from '@/utils/request'

const api_name = '/eduvod/video'

export default {
  // 获取视频播放凭证
  getPlayAuth(videoId) {
    return request({
      url: `${api_name}/getPlayAuth/${videoId}`,
      method: 'get'
    })
  }  
}
