import request from '@/utils/request'

const api_name = '/eduservice/teacherfront'

export default {
  // 分页查询讲师
  getTeacherList(page, limit) {
    return request({
      url: `${api_name}/pageList/${page}/${limit}`,
      method: 'get'
    })
  },

  // 获取讲师详情
  getTeacherInfo(teacherId) {
    return request({
      url: `${api_name}/getTeacherInfo/${teacherId}`,
      method: 'get'
    })
  }
}