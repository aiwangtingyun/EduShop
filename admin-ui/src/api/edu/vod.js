import request from '@/utils/request'

const api_name = '/eduvod/video'

export default {
  // 删除阿里云视频
  deleteAliyunVod(videoId) {
    return request({
      url: `${api_name}/removeVideo/${videoId}`,
      method: 'delete'
    })
  }
}
