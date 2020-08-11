import request from '@/utils/request'

const api_name = '/eduservice/course'

export default {
  // 添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: `${api_name}/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  // 根据课程id查询课程基本信息
  getCourseInfo(courseId) {
    return request({
      url: `${api_name}/getCourseInfo/${courseId}`,
      method: 'get'
    })
  },
  // 修改课程基本信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `${api_name}/updateCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  // 查询所有讲师
  getListTeacher() {
    return request({
      url: '/eduservice/admin/teachers',
      method: 'get',
    })
  }
}
