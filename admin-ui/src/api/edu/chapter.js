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
  addChapter(chapter) {
    return request({
      url: `${api_name}/addChapter`,
      method: 'post',
      data: chapter
    })
  },

  // 根据id查询章节信息
  getChapterInfo(chapterId) {
    return request({
      url: `${api_name}/getChapterInfo/${chapterId}`,
      method: 'get'
    })
  },

  // 修改章节
  updateChapter(chapter) {
    return request({
      url: `${api_name}/updateChapterInfo`,
      method: 'post',
      data: chapter
    })
  },

  // 删除章节
  deleteChapter(chapterId) {
    return request({
      url: `${api_name}/deleteChapter/${chapterId}`,
      method: 'delete'
    })
  }
}
