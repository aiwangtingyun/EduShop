import request from '@/utils/request'

const api_name = '/eduservice/coursefront'

export default {
  // 分页课程讲师
  getCourseList(page, limit, queryObj) {
    return request({
      url: `${api_name}/getCourseList/${page}/${limit}`,
      method: 'post',
      data: queryObj
    })
  },

  // 查询所有课程分类
  getAllSubject() {
    return request({
      url: `/eduservice/subject/getAllSubject`,
      method: 'get'
    })
  },

  // 获取课程详情
  getCourseInfo(courseId) {
    return request({
      url: `${api_name}/getWebCourseInfo/${courseId}`,
      method: 'get'
    })
  }
}