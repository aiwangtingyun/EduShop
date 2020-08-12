import request from '@/utils/request'

const api_name = '/eduservice/video'

export default {
  // 添加小节
  addVideo(video) {
    return request({
      url: `${api_name}/addVideo`,
      method: 'post',
      data: video
    })
  },

  // 根据id获取小节信息
  getVideoInfo(id) {
    return request({
      url: `${api_name}/getVideoInfo/${id}`,
      method: 'get'
    })
  },

  // 修改小节
  updateVideo(video) {
    return request({
      url: `${api_name}/updateVideo`,
      method: "post",
      data: video
    })
  },

  // 删除小节
  deleteVideo(id) {
    return request({
      url: `${api_name}/deleteVideo/${id}`,
      method: 'delete'
    })
  }
}
