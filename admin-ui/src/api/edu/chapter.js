import request from '@/utils/request'

const api_name = '/eduservice/chapter'

export default {
  // 根据课程id值获取章节和小结数据列表
  getAllChapterVideo(courseId) {
    return request({
      url: `${api_name}/getChapterVideo/${courseId}`,
      method: 'get'
    })
  },
  // 添加章节
}
