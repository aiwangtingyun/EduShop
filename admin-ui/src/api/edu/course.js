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
      url: '/eduservice/teacher/list',
      method: 'get',
    })
  },
  // 获取课程发布最终确认信息
  getPublishCourseInfo(courseId) {
    return request({
      url: `${api_name}/getPublishCourseInfo/${courseId}`,
      method: 'get'
    })
  },
  // 课程最终发布
  publishCourse(courseId) {
    return request({
      url: `${api_name}/publishCourse/${courseId}`,
      method: 'post'
    })
  },
  // 获取课程列表
  getCoursePageList(page, limit, courseQuery) {
    return request({
      url: `${api_name}/pageList/${page}/${limit}`,
      method: 'post',
      data: courseQuery
    })
  },

  // 根据课程id删除课程
  deleteCourseById(courseId) {
    return request({
      url: `${api_name}/deleteCourse/${courseId}`,
      method: 'delete'
    })
  }
}
